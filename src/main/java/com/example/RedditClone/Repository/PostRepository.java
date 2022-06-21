package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Post;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAll();

//    @Query(nativeQuery = true, value = "select * from posts order by  rand() limit 12")
    @Query(nativeQuery = true, value = "select * from posts p left join communities c on p.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where p.post_id = r.post_id and r.accepted = true)) " +
            "order by rand() limit 12;")
    List<Post> find12RandomPosts();

    List<Post> findAllByOrderByCreationDateDesc();
    Post findPostById(Integer id);
}
