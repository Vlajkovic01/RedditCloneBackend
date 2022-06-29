package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.Entity.*;
import com.example.RedditClone.Model.Enum.ReactionType;
import com.example.RedditClone.Repository.PostRepository;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.*;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    private final BannedService bannedService;
    private final LogService logService;
    private final ReactionRepository reactionRepository;
    private final FlairService flairService;
    private final UserService userService;

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, FlairService flairService, ReactionRepository reactionRepository, LogService logService, BannedService bannedService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.flairService = flairService;
        this.reactionRepository = reactionRepository;
        this.logService = logService;
        this.bannedService = bannedService;
    }

    @Override
    public List<Post> findAll() {
        logService.message("Post service, findAll() method called.", MessageType.INFO);
        return postRepository.findAll();
    }

    @Override
    public List<Post> find12RandomPosts() {
        logService.message("Post service, find12RandomPosts() method called.", MessageType.INFO);
        return postRepository.find12RandomPosts();
    }

    @Override
    public List<Post> newSort() {
        logService.message("Post service, newSort() method called.", MessageType.INFO);
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Post> newSortInCommunity(Community community) {
        logService.message("Post service, newSortInCommunity() method called.", MessageType.INFO);
        return postRepository.findPostsByCommunityOrderByCreationDateDesc(community);
    }

    @Override
    public Set<Post> topSort() {
        logService.message("Post service, topSort() method called.", MessageType.INFO);
        return postRepository.findAllOrderByKarmaDesc();
    }

    @Override
    public Set<Post> topSortInCommunity(Community community) {
        logService.message("Post service, topSortInCommunity() method called.", MessageType.INFO);
        return postRepository.findAllByCommunityOrderByKarmaDesc(community.getId());
    }

    @Override
    public Set<Post> hotSort() {
        logService.message("Post service, hotSort() method called.", MessageType.INFO);
        return postRepository.findAllOrderByKarmaAndCreationDate();
    }

    @Override
    public Set<Post> hotSortInCommunity(Community community) {
        logService.message("Post service, hotSortInCommunity() method called.", MessageType.INFO);
        return postRepository.findAllByCommunityOrderByKarmaAndCreationDate(community.getId());
    }

    @Override
    public Post findPostById(Integer id) {
        logService.message("Post service, findPostById() method called.", MessageType.INFO);
        return postRepository.findPostById(id);
    }

    @Override
    public Post createPost(PostCreateRequestDTO postCreateRequestDTO, Authentication authentication, Community community) {

        logService.message("Post service, createPost() method called.", MessageType.INFO);

        if (authentication == null) {
            logService.message("Post service, createPost() method, authentication is null.", MessageType.INFO);
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Post service, createPost() method, current logged user is null.", MessageType.INFO);
            return null;
        }

        if (bannedService.findBannedByCommunityIdAndUserUsername(community.getId(), currentLoggedUser.getUsername()) != null) {
            logService.message("Post service, createPost() method, current logged user is banned from this community.", MessageType.WARN);
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

        logService.message("Post service, editPost() method called.", MessageType.INFO);

        postForEdit.setText(postEditRequestDTO.getText());

        if (!postEditRequestDTO.getImagePath().equals("")) {
            postForEdit.setImagePath(postEditRequestDTO.getImagePath());
        }

        if (!postEditRequestDTO.getFlair().getName().equals("")) {
            postForEdit.setFlair(flairService.findFlairByName(postEditRequestDTO.getFlair().getName()));
        }

        postForEdit = postRepository.save(postForEdit);
        return postForEdit;
    }

    @Override
    public void deletePost(Integer idPost) {
        logService.message("Post service, deletePost() method called.", MessageType.INFO);
        postRepository.deletePost(idPost);
    }
}
