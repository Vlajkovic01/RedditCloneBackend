package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();
    User findUserByUsernameAndPassword(String username, String password);

    Optional<User> findFirstByUsername(String username);
    User findUserById(Integer id);

    @Query(nativeQuery = true, value = "select count(*) from posts where post_id = ? and user_id = ?")
    Integer imIPostCreator(Integer idPost, Integer idUser);
    @Query(nativeQuery = true, value = "select (select count(*) from reactions r " +
            "left join posts p on r.post_id = p.post_id " +
            "left join comments c on r.comment_id = c.comment_id " +
            "where (p.user_id = ?1 or c.user_id = ?1) and r.type = 'UPVOTE') " +
            "- (select count(*) from reactions r " +
            "left join posts p on r.post_id = p.post_id " +
            "left join comments c on r.comment_id = c.comment_id " +
            "where (p.user_id = ?1 or c.user_id = ?1) and r.type = 'DOWNVOTE') AS Difference")
    Integer findTotalKarmaByUserId(Integer id);
}
