package com.example.RedditClone.repository.jpa;

import com.example.RedditClone.model.entity.Flair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlairRepository extends JpaRepository<Flair, Integer> {

    Flair findFlairByName(String name);
}
