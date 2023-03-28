package com.example.RedditClone.controller;

import com.example.RedditClone.model.dto.reaction.request.ReactionCreateRequestDTO;
import com.example.RedditClone.model.dto.reaction.response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.model.entity.Reaction;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.service.ReactionService;
import com.example.RedditClone.model.mapper.ExtendedModelMapper;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/reactions")
public class ReactionController {

    private final LogService logService;
    private final ReactionService reactionService;
    private final ExtendedModelMapper modelMapper;

    public ReactionController(ExtendedModelMapper modelMapper, ReactionService reactionService, LogService logService) {
        this.modelMapper = modelMapper;
        this.reactionService = reactionService;
        this.logService = logService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
    public ResponseEntity<ReactionForPostAndCommentDTO> createReaction(@RequestBody ReactionCreateRequestDTO newReaction,
                                                                    Authentication authentication) {

        logService.message("Reaction controller, createReaction() method called.", MessageType.INFO);

        Reaction createdReaction = reactionService.createReaction(newReaction, authentication);

        if(createdReaction == null){
            logService.message("Reaction controller, createReaction() method, failed to create a reaction.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ReactionForPostAndCommentDTO reactionDTO = modelMapper.map(createdReaction, ReactionForPostAndCommentDTO.class);
        return new ResponseEntity<>(reactionDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<ReactionForPostAndCommentDTO> deleteReaction(@PathVariable Integer id, Authentication authentication) {

        logService.message("Reaction controller, deleteReaction() method called.", MessageType.INFO);

        Reaction reactionForDelete = reactionService.findById(id);

        if(reactionForDelete == null){
            logService.message("Reaction controller, deleteReaction() method, failed to find a reaction.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        reactionService.delete(reactionForDelete);

        ReactionForPostAndCommentDTO reactionDTO = modelMapper.map(reactionForDelete, ReactionForPostAndCommentDTO.class);
        return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
    }
}
