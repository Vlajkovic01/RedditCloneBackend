package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {
}
