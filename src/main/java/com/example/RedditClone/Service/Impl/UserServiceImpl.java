package com.example.RedditClone.Service.Impl;


import com.example.RedditClone.Model.DTO.User.Request.UserEditPasswordRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserEditRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Repository.UserRepository;
import com.example.RedditClone.Security.TokenUtils;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.UserService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final LogService logService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, LogService logService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public List<User> findAll() {
        logService.message("User service, findAll() method called.", MessageType.INFO);
        return userRepository.findAll();
    }
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        logService.message("User service, findUserByUsernameAndPassword() method called.", MessageType.INFO);
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public User findUserById(Integer id) {
        logService.message("User service, findUserById() method called.", MessageType.INFO);
        return userRepository.findUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        logService.message("User service, findByUsername() method called.", MessageType.INFO);
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(@Validated UserRegisterRequestDTO userDTO) {

        logService.message("User service, createUser() method called.", MessageType.INFO);

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setRegistrationDate(LocalDate.now());

        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User editUser(@Validated UserEditRequestDTO userEditRequestDTO, User currentLoggedUser) {

        logService.message("User service, editUser() method called.", MessageType.INFO);

        if (!userEditRequestDTO.getAvatar().equals("")) {
            currentLoggedUser.setAvatar(userEditRequestDTO.getAvatar());
        }

        if (userEditRequestDTO.getDescription() != null) {
            currentLoggedUser.setDescription(userEditRequestDTO.getDescription());
        } else {
            currentLoggedUser.setDescription(null);
        }

        if (userEditRequestDTO.getDisplayName() != null) {
            currentLoggedUser.setDisplayName(userEditRequestDTO.getDisplayName());
        } else {
            currentLoggedUser.setDisplayName(null);
        }

        currentLoggedUser = userRepository.save(currentLoggedUser);
        return currentLoggedUser;
    }

    @Override
    public User editPassword(UserEditPasswordRequestDTO userEditPasswordRequestDTO, User currentLoggedUser) {

        logService.message("User service, editPassword() method called.", MessageType.INFO);

        if (userEditPasswordRequestDTO.getCurrentPassword() != null) {
            if (passwordEncoder.matches(userEditPasswordRequestDTO.getCurrentPassword(), currentLoggedUser.getPassword())) {
                currentLoggedUser.setPassword(passwordEncoder.encode(userEditPasswordRequestDTO.getNewPassword()));
            } else {
                return null;
            }
        }
        currentLoggedUser = userRepository.save(currentLoggedUser);
        return currentLoggedUser;
    }

    @Override
    public boolean amIPostCreator(Integer idPost, Integer idUser) {
        logService.message("User service, amIPostCreator() method called.", MessageType.INFO);
        return userRepository.imIPostCreator(idPost, idUser) > 0;
    }

    @Override
    public Integer findTotalKarmaByUserId(Integer id) {
        logService.message("User service, findTotalKarmaByUserId() method called.", MessageType.INFO);
        return userRepository.findTotalKarmaByUserId(id);
    }
}
