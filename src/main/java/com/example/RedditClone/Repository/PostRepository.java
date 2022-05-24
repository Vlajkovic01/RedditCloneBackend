package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAll();
    Post findPostById(Integer id);
//    void deletePost(Integer idPost);
}
