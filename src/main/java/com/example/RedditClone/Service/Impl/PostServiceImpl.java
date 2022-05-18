package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Flair;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.PostRepository;
import com.example.RedditClone.Service.FlairService;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private FlairService flairService;
    private final UserService userService;

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, FlairService flairService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.flairService = flairService;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return null;
        }


        Post newPost = new Post();
        newPost.setTitle(postCreateRequestDTO.getTitle());
        newPost.setText(postCreateRequestDTO.getText());
        newPost.setCreationDate(LocalDate.now());
        newPost.setImagePath(postCreateRequestDTO.getImagePath());
        newPost.setUser(currentLoggedUser);

        if (postCreateRequestDTO.getFlair() != null) {
            newPost.setFlair(flairService.findFlairByName(postCreateRequestDTO.getFlair().getName()));
        }
        newPost = postRepository.save(newPost);
        return newPost;
    }
}
