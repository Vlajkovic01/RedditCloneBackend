package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.Entity.*;
import com.example.RedditClone.Model.Enum.ReactionType;
import com.example.RedditClone.Repository.PostRepository;
import com.example.RedditClone.Repository.ReactionRepository;
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

    private final ReactionRepository reactionRepository;
    private FlairService flairService;
    private final UserService userService;

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, FlairService flairService, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.flairService = flairService;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> find12RandomPosts() {
        return postRepository.find12RandomPosts();
    }

    @Override
    public Post findPostById(Integer id) {
        return postRepository.findPostById(id);
    }

    @Override
    public Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication, Community community) {

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
        newPost.setCommunity(community);

        if (postCreateRequestDTO.getFlair() != null) {
            newPost.setFlair(flairService.findFlairByName(postCreateRequestDTO.getFlair().getName()));
        }

        Reaction newReaction = new Reaction();
        newReaction.setType(ReactionType.UPVOTE);
        newReaction.setTimestamp(LocalDate.now());
        newReaction.setUser(currentLoggedUser);
        newReaction.setPost(newPost);


        newPost = postRepository.save(newPost);
        newReaction = reactionRepository.save(newReaction);
        return newPost;
    }

    @Override
    public Post editPost(PostEditRequestDTO postEditRequestDTO, Post postForEdit) {
        postForEdit.setText(postEditRequestDTO.getText());
        postForEdit.setImagePath(postEditRequestDTO.getImagePath());

        if (postEditRequestDTO.getFlair() != null) {
            postForEdit.setFlair(flairService.findFlairByName(postEditRequestDTO.getFlair().getName()));
        }

        postForEdit = postRepository.save(postForEdit);
        return postForEdit;
    }

    @Override
    public void deletePost(Integer idPost) {
        postRepository.deleteById(idPost);
    }
}
