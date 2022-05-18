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
}
