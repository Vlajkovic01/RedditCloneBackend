package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Comment.Request.CommentCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Comment.Response.CommentGetForPostDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Comment;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Service.CommentService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/comments")
public class CommentController {

    private final PostService postService;
    private final LogService logService;
    private final CommentService commentService;
    private final ExtendedModelMapper modelMapper;

    public CommentController(ExtendedModelMapper modelMapper, CommentService commentService, LogService logService, PostService postService) {
        this.modelMapper = modelMapper;
        this.commentService = commentService;
        this.logService = logService;
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
    public ResponseEntity<CommentGetForPostDTO> createComment(@RequestBody @Validated CommentCreateRequestDTO newComment,
                                                                   Authentication authentication) {

        logService.message("Comment controller, createComment() method called.", MessageType.INFO);

        Comment createdComment = commentService.createComment(newComment, authentication);

        if(createdComment == null){
            logService.message("Comment controller, createComment() method, failed to create a comment.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentGetForPostDTO commentDTO = modelMapper.map(createdComment, CommentGetForPostDTO.class);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/post/{id}/new")
    public ResponseEntity<List<CommentGetForPostDTO>> getNewCommentsForPost(@PathVariable Integer id) {

        logService.message("Comment controller, getNewCommentForPost() method called.", MessageType.INFO);

        Post post = postService.findPostById(id);

        if(post == null){
            logService.message("Comment controller, getNewCommentForPost() method, failed to find a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Set<Comment> comments = commentService.newSort(post);

        List<CommentGetForPostDTO> commentsDTO = modelMapper.mapAll(comments.stream().toList(), CommentGetForPostDTO.class);

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping("/post/{id}/old")
    public ResponseEntity<List<CommentGetForPostDTO>> getOldCommentsForPost(@PathVariable Integer id) {

        logService.message("Comment controller, getOldCommentForPost() method called.", MessageType.INFO);

        Post post = postService.findPostById(id);

        if(post == null){
            logService.message("Comment controller, getOldCommentForPost() method, failed to find a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Set<Comment> comments = commentService.oldSort(post);

        List<CommentGetForPostDTO> commentsDTO = modelMapper.mapAll(comments.stream().toList(), CommentGetForPostDTO.class);

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping("/post/{id}/top")
    public ResponseEntity<List<CommentGetForPostDTO>> getTopCommentsForPost(@PathVariable Integer id) {

        logService.message("Comment controller, getTopCommentForPost() method called.", MessageType.INFO);

        Post post = postService.findPostById(id);

        if(post == null){
            logService.message("Comment controller, getTopCommentForPost() method, failed to find a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Set<Comment> comments = commentService.topSort(post);

        List<CommentGetForPostDTO> commentsDTO = modelMapper.mapAll(comments.stream().toList(), CommentGetForPostDTO.class);

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }
}
