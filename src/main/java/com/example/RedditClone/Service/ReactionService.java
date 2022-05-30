package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Reaction.Response.ReactionForPostAndCommentDTO;
import com.example.RedditClone.Model.Entity.Post;

import java.util.List;

public interface ReactionService {

    void deleteAllByPost(Post post);
    List<ReactionForPostAndCommentDTO> findAllByPostId(Integer id);
}
