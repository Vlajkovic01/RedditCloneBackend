package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.user.request.UserEditPasswordRequestDTO;
import com.example.RedditClone.model.dto.user.request.UserEditRequestDTO;
import com.example.RedditClone.model.dto.user.request.UserRegisterRequestDTO;
import com.example.RedditClone.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserByUsernameAndPassword(String username, String password);
    User findUserById(Integer id);

    User findByUsername(String username);

    User createUser(UserRegisterRequestDTO userDTO);

    User editUser(UserEditRequestDTO userEditRequestDTO, User currentLoggedUser);
    User editPassword(UserEditPasswordRequestDTO userEditPasswordRequestDTO, User currentLoggedUser);

    boolean amIPostCreator(Integer idPost, Integer idUser);

    Integer findTotalKarmaByUserId(Integer id);
}
