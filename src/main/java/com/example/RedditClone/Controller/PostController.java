package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {

    private final LogService logService;
    private final ExtendedModelMapper modelMapper;

    private final PostService postService;

    public PostController(ExtendedModelMapper modelMapper, PostService postService, LogService logService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<PostGetAllResponseDTO>> getRandomPosts() {

        logService.message("Post controller, getRandomPosts() method called.", MessageType.INFO);

        List<Post> posts = postService.find12RandomPosts();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts, PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }
}
