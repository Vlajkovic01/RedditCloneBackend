package com.example.RedditClone.repository;

import com.example.RedditClone.model.entity.Community;
import com.example.RedditClone.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAll();

    @Query(nativeQuery = true, value = "select * from posts p left join communities c on p.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where p.post_id = r.post_id and r.accepted = true)) " +
            "order by rand() limit 12;")
    List<Post> find12RandomPosts();

    List<Post> findAllByOrderByCreationDateDesc();
    List<Post> findPostsByCommunityOrderByCreationDateDesc(Community community);
    @Query(nativeQuery = true, value = "select *, (select count(*) " +
            "from reactions r left join posts p on r.post_id = p.post_id " +
            "where r.type = 'UPVOTE' and p.post_id = post.post_id) - (select count(*) " +
            "from reactions r left join posts p on r.post_id = p.post_id " +
            "where r.type = 'DOWNVOTE' and p.post_id = post.post_id) as karma " +
            "from posts post left join communities c on post.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where post.post_id = r.post_id and r.accepted = true)) " +
            "order by karma desc")
    Set<Post> findAllOrderByKarmaDesc();
    @Query(nativeQuery = true, value = "select *, (select count(*) " +
            "from reactions r left join posts p on r.post_id = p.post_id " +
            "where r.type = 'UPVOTE' and p.post_id = post.post_id) - (select count(*) " +
            "from reactions r left join posts p on r.post_id = p.post_id " +
            "where r.type = 'DOWNVOTE' and p.post_id = post.post_id) as karma " +
            "from posts post left join communities c on post.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where post.post_id = r.post_id and r.accepted = true)) and post.community_id = ? " +
            "order by karma desc")
    Set<Post> findAllByCommunityOrderByKarmaDesc(Integer communityId);
    @Query(nativeQuery = true, value = "select *, (select count(*) " +
            "            from reactions r left join posts p on r.post_id = p.post_id " +
            "            where r.type = 'UPVOTE' and p.post_id = post.post_id) - (select count(*) " +
            "            from reactions r left join posts p on r.post_id = p.post_id " +
            "            where r.type = 'DOWNVOTE' and p.post_id = post.post_id) as karma, datediff(curdate(), post.creation_date) as daysdiff " +
            "from posts post left join communities c on post.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where post.post_id = r.post_id and r.accepted = true)) " +
            "order by (karma * 1) - (daysdiff * 1.2) desc")
    Set<Post> findAllOrderByKarmaAndCreationDate();
    @Query(nativeQuery = true, value = "select *, (select count(*) " +
            "            from reactions r left join posts p on r.post_id = p.post_id " +
            "            where r.type = 'UPVOTE' and p.post_id = post.post_id) - (select count(*) " +
            "            from reactions r left join posts p on r.post_id = p.post_id " +
            "            where r.type = 'DOWNVOTE' and p.post_id = post.post_id) as karma, datediff(curdate(), post.creation_date) as daysdiff " +
            "from posts post left join communities c on post.community_id = c.community_id " +
            "where c.is_suspended = false and (not exists(select * from reports r where post.post_id = r.post_id and r.accepted = true)) and post.community_id = ? " +
            "order by (karma * 1) - (daysdiff * 1.2) desc")
    Set<Post> findAllByCommunityOrderByKarmaAndCreationDate(Integer id);
    Post findPostById(Integer id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from posts where post_id = ?")
    void deletePost(Integer postId);
}
