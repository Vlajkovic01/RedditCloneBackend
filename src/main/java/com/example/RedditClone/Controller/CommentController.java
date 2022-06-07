package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Comment.Response.CommentGetForPostDTO;
import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Service.CommentService;
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

@RestController
@RequestMapping(value = "api/comments")
public class CommentController {
    private final CommentService commentService;
    private final ExtendedModelMapper modelMapper;

    public CommentController(ExtendedModelMapper modelMapper, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
    public ResponseEntity<CommentGetForPostDTO> createComment(@RequestBody @Validated CommentCreateRequestDTO newComment,
                                                                   Authentication authentication) {

        Comment createdComment = commentService.createComment(newComment, authentication);

        if(createdComment == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentGetForPostDTO commentDTO = modelMapper.map(createdComment, CommentGetForPostDTO.class);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }
}
