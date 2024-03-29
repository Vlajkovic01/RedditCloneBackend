package com.example.RedditClone.service.impl;

import com.example.RedditClone.model.dto.pdf.PDFResponseDTO;
import com.example.RedditClone.model.dto.post.request.PostCreateRequestDTO;
import com.example.RedditClone.model.dto.post.request.PostEditRequestDTO;
import com.example.RedditClone.model.entity.*;
import com.example.RedditClone.model.enumeration.ReactionType;
import com.example.RedditClone.model.indexed.IndexedPost;
import com.example.RedditClone.repository.jpa.PostRepository;
import com.example.RedditClone.repository.jpa.ReactionRepository;
import com.example.RedditClone.service.*;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    private final CommunityService communityService;
    private final IndexedPostService indexedPostService;
    private final IndexedCommunityService indexedCommunityService;
    private final BannedService bannedService;
    private final LogService logService;
    private final ReactionRepository reactionRepository;
    private final FlairService flairService;
    private final UserService userService;
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, FlairService flairService, ReactionRepository reactionRepository, LogService logService, BannedService bannedService, IndexedPostService indexedPostService, CommunityService communityService, IndexedCommunityService indexedCommunityService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.flairService = flairService;
        this.reactionRepository = reactionRepository;
        this.logService = logService;
        this.bannedService = bannedService;
        this.indexedPostService = indexedPostService;
        this.communityService = communityService;
        this.indexedCommunityService = indexedCommunityService;
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

        if (postCreateRequestDTO.getPdf() == null) {
            newPost = postRepository.save(newPost);
            indexedPostService.indexPost(newPost, "");
        } else {
            newPost.setPdfFileName(postCreateRequestDTO.getPdf().getFilename());
            newPost = postRepository.save(newPost);
            indexedPostService.indexPost(newPost, postCreateRequestDTO.getPdf().getPdfText());
        }

        community.getPosts().add(newPost);
        communityService.save(community);
        indexedCommunityService.updateNumOfPostAndAvgKarma(community, +1);

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

        if (postEditRequestDTO.getPdf() == null) {
            postForEdit = postRepository.save(postForEdit);
            IndexedPost indexedPost = indexedPostService.findById(postForEdit.getId());
            indexedPostService.indexPost(postForEdit, indexedPost.getPdfText());
        } else {
            postForEdit.setPdfFileName(postEditRequestDTO.getPdf().getFilename());
            postForEdit = postRepository.save(postForEdit);
            indexedPostService.indexPost(postForEdit, postEditRequestDTO.getPdf().getPdfText());
        }
        return postForEdit;
    }

    @Override
    public void deletePost(Integer idPost) {
        logService.message("Post service, deletePost() method called.", MessageType.INFO);

        Integer communityId = postRepository.findPostById(idPost).getCommunity().getId();
        postRepository.deletePost(idPost);

        indexedPostService.deleteById(idPost);
        indexedCommunityService.
                updateNumOfPostAndAvgKarma(communityService.findCommunityById(communityId), -1);
    }
}
