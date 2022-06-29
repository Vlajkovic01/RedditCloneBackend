package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.*;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    private final BannedService bannedService;
    private final LogService logService;
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    private final ExtendedModelMapper modelMapper;

    private final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, ExtendedModelMapper modelMapper, UserService userService, PostService postService, CommentService commentService, LogService logService, BannedService bannedService) {
        this.reactionRepository = reactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.logService = logService;
        this.bannedService = bannedService;
    }

    @Override
    public void deleteAllByPost(Post post) {
        logService.message("Reaction service, deleteAllByPost() method called.", MessageType.INFO);
        reactionRepository.deleteAllByPost(post);
    }

    @Override
    public List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id) {
        logService.message("Reaction service, findAllByPost() method called.", MessageType.INFO);
        List<Reaction> reactions = reactionRepository.findAllByPostId(id);
        return modelMapper.mapAll(reactions, ReactionForPostAndCommentDTO.class);
    }

    @Override
    public Reaction createReaction(ReactionCreateRequestDTO reactionCreateRequestDTO, Authentication authentication) {

        logService.message("Reaction service, createReaction() method called.", MessageType.INFO);

        if (authentication == null) {
            logService.message("Reaction service, createReaction() method, authentication is null.", MessageType.WARN);
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            logService.message("Reaction service, createReaction() method, current logged user is null.", MessageType.WARN);
            return null;
        }

        Reaction newReaction = new Reaction();
        newReaction.setType(reactionCreateRequestDTO.getType());
        newReaction.setTimestamp(LocalDate.now());
        newReaction.setUser(currentLoggedUser);

        if (reactionCreateRequestDTO.getPostId() != 0) {
            Reaction reactionPost = reactionRepository.findReactionByPostAndUser(
                    postService.findPostById(reactionCreateRequestDTO.getPostId()),
                    currentLoggedUser);
            if (reactionPost != null) {
                return null;
            }
            Post post = postService.findPostById(reactionCreateRequestDTO.getPostId());
            if (bannedService.findBannedByCommunityIdAndUserUsername(post.getCommunity().getId(), currentLoggedUser.getUsername()) != null) {
                logService.message("Reaction service, createReaction() method, current logged user is banned from this community.", MessageType.WARN);
                return null;
            }
            newReaction.setPost(post);
        }

        if (reactionCreateRequestDTO.getCommentId() != 0) {
            Reaction reactionComment = reactionRepository.findReactionByCommentAndUser(
                    commentService.findCommentById(reactionCreateRequestDTO.getCommentId()),
                    currentLoggedUser);
            if (reactionComment != null) {
                return null;
            }
            newReaction.setComment(commentService.findCommentById(reactionCreateRequestDTO.getCommentId()));
        }

        reactionRepository.save(newReaction);

        return newReaction;
    }

    @Override
    public void delete(Reaction reaction) {
        reactionRepository.delete(reaction);
    }

    @Override
    public void save(Reaction reaction) {
        reactionRepository.save(reaction);
    }
}
