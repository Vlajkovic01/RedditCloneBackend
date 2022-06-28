package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    @Query(nativeQuery = true, value = "select count(*) from moderator where community_id = ? and user_id = ?")
    Integer imIModerator(Integer idCommunity, Integer idUser);

    Moderator findModeratorById(Integer id);
}
