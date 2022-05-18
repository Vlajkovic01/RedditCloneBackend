package com.example.RedditClone.Repository;

import com.example.RedditClone.Model.Entity.Flair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlairRepository extends JpaRepository<Flair, Integer> {

    Flair findFlairByName(String name);
}
