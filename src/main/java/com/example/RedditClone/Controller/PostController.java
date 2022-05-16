package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {

    private final ExtendedModelMapper modelMapper;

    private final PostService postService;

    public PostController(ExtendedModelMapper modelMapper, PostService postService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostGetAllResponseDTO>> getPosts() {

        List<Post> posts = postService.findAll();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts, PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }
}
