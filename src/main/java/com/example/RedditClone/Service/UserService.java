package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.User.Request.UserEditPasswordRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserEditRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.Entity.User;
import org.springframework.data.jpa.repository.Query;

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
