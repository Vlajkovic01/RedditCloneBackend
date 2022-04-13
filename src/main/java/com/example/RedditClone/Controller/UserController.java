package com.example.RedditClone.Controller;

import com.example.RedditClone.Util.MyModelMapper;
import com.example.RedditClone.Model.DTO.UserDTO;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private final MyModelMapper modelMapper;
    private final UserService userService;

    public UserController(UserService userService, MyModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {

        List<User> users = userService.findAll();

        List<UserDTO> usersDTO = modelMapper.mapAll(users, UserDTO.class);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<UserDTO> getUserByUsernameAndPassword(@RequestParam String username,
                                                                @RequestParam String password) {

        User user = userService.findUserByUsernameAndPassword(username, password);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
