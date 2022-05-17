package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.Entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User createUser(UserRegisterRequestDTO userDTO);
}
