package com.example.RedditClone.controller;

import com.example.RedditClone.model.dto.indexedPost.request.IndexedPostSearchDTO;
import com.example.RedditClone.model.dto.indexedPost.response.IndexedPostResponseDTO;
import com.example.RedditClone.model.dto.post.response.PostGetAllResponseDTO;
import com.example.RedditClone.model.entity.Post;
import com.example.RedditClone.service.IndexedPostService;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.service.PostService;
import com.example.RedditClone.model.mapper.ExtendedModelMapper;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {

    private static final int MAX_SEARCH_FIELDS = 5 ;
    private final IndexedPostService indexedPostService;
    private final LogService logService;
    private final ExtendedModelMapper modelMapper;

    private final PostService postService;

    public PostController(ExtendedModelMapper modelMapper, PostService postService, LogService logService, IndexedPostService indexedPostService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
        this.logService = logService;
        this.indexedPostService = indexedPostService;
    }

    @GetMapping
    public ResponseEntity<List<PostGetAllResponseDTO>> getRandomPosts() {

        logService.message("Post controller, getRandomPosts() method called.", MessageType.INFO);

        List<Post> posts = postService.find12RandomPosts();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts, PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<List<PostGetAllResponseDTO>> getNewPosts() {

        logService.message("Post controller, getNewPosts() method called.", MessageType.INFO);

        List<Post> posts = postService.newSort();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts, PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<PostGetAllResponseDTO>> getTopPosts() {

        logService.message("Post controller, getTopPosts() method called.", MessageType.INFO);

        Set<Post> posts = postService.topSort();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts.stream().toList(), PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<PostGetAllResponseDTO>> getHotPosts() {

        logService.message("Post controller, getHotPosts() method called.", MessageType.INFO);

        Set<Post> posts = postService.hotSort();

        List<PostGetAllResponseDTO> postsDTO = modelMapper.mapAll(posts.stream().toList(), PostGetAllResponseDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<IndexedPostResponseDTO>> search(@RequestParam Map<String,String> searchParams) {
        if (searchParams.isEmpty() || searchParams.size() > MAX_SEARCH_FIELDS){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            List<IndexedPostResponseDTO> posts = indexedPostService.search(searchParams);
            return new ResponseEntity<>(posts,HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
