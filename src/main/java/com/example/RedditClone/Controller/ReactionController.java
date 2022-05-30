package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Service.ReactionService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping(value = "api/reactions")
public class ReactionController {

    private final ReactionService reactionService;
    private final ExtendedModelMapper modelMapper;

    public ReactionController(ExtendedModelMapper modelMapper, ReactionService reactionService) {
        this.modelMapper = modelMapper;
        this.reactionService = reactionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
    public ResponseEntity<ReactionCreateRequestDTO> createReaction(@RequestBody ReactionCreateRequestDTO newReaction,
                                                                    Authentication authentication) {

        Reaction createdReaction = reactionService.createReaction(newReaction, authentication);

        if(createdReaction == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ReactionCreateRequestDTO reactionDTO = modelMapper.map(createdReaction, ReactionCreateRequestDTO.class);
        return new ResponseEntity<>(reactionDTO, HttpStatus.CREATED);
    }
}
