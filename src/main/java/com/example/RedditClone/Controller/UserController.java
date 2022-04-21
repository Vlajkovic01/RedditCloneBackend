package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.User.Request.UserGetAllRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserLoginRequestDTO;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private final ExtendedModelMapper modelMapper;
    private final UserService userService;

    public UserController(UserService userService, ExtendedModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserGetAllRequestDTO>> getUsers() {

        List<User> users = userService.findAll();

        List<UserGetAllRequestDTO> usersDTO = modelMapper.mapAll(users, UserGetAllRequestDTO.class);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        User user = userService.findUserByUsernameAndPassword(userLoginRequestDTO.getUsername(),
                userLoginRequestDTO.getPassword());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("User found",HttpStatus.OK);
    }
}
