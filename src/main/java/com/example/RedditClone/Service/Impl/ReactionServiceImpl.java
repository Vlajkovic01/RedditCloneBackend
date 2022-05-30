package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Model.DTO.Reaction.Request.ReactionCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Reaction;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.ReactionRepository;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Service.ReactionService;
import com.example.RedditClone.Service.UserService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final PostService postService;
    private final UserService userService;
    private final ExtendedModelMapper modelMapper;

    private final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, ExtendedModelMapper modelMapper, UserService userService, PostService postService) {
        this.reactionRepository = reactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void deleteAllByPost(Post post) {
        reactionRepository.deleteAllByPost(post);
    }

    @Override
    public List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id) {
        List<Reaction> reactions = reactionRepository.findAllByPostId(id);
        return modelMapper.mapAll(reactions, ReactionForPostAndCommentDTO.class);
    }

    @Override
    public Reaction createReaction(ReactionCreateRequestDTO reactionCreateRequestDTO, Authentication authentication) {


        if (authentication == null) {
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return null;
        }

        Reaction reaction = reactionRepository.findReactionByPostAndUser(
                                                postService.findPostById(reactionCreateRequestDTO.getPostId()),
                                                currentLoggedUser);
        if (reaction != null) {
            return null;
        }

        Reaction newReaction = new Reaction();
        newReaction.setType(reactionCreateRequestDTO.getType());
        newReaction.setTimestamp(LocalDate.now());
        newReaction.setUser(currentLoggedUser);
        newReaction.setPost(postService.findPostById(reactionCreateRequestDTO.getPostId()));

        reactionRepository.save(newReaction);

        return newReaction;
    }
}
