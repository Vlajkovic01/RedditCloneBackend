package com.example.RedditClone.controller;

import com.example.RedditClone.model.dto.user.request.UserEditPasswordRequestDTO;
import com.example.RedditClone.model.dto.user.request.UserEditRequestDTO;
import com.example.RedditClone.model.dto.user.request.UserRegisterRequestDTO;
import com.example.RedditClone.model.dto.user.response.UserForMyProfileDTO;
import com.example.RedditClone.model.dto.user.response.UserGetAllResponseDTO;
import com.example.RedditClone.model.dto.user.request.UserLoginRequestDTO;
import com.example.RedditClone.model.dto.user.response.UserTokenState;
import com.example.RedditClone.security.TokenUtils;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.service.ModeratorService;
import com.example.RedditClone.model.mapper.ExtendedModelMapper;
import com.example.RedditClone.model.entity.User;
import com.example.RedditClone.service.UserService;
import com.example.RedditClone.model.enumeration.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private final LogService logService;
    private ModeratorService moderatorService;
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ExtendedModelMapper modelMapper;
    private final UserService userService;

    public UserController(UserService userService, ExtendedModelMapper modelMapper, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ModeratorService moderatorService, LogService logService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.moderatorService = moderatorService;
        this.logService = logService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<UserGetAllResponseDTO>> getUsers() {

        logService.message("User controller, getUsers() method called.", MessageType.INFO);

        List<User> users = userService.findAll();

        List<UserGetAllResponseDTO> usersDTO = modelMapper.mapAll(users, UserGetAllResponseDTO.class);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserForMyProfileDTO> getUser(@PathVariable String username) {

        logService.message("User controller, getUser() method called.", MessageType.INFO);

        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        UserForMyProfileDTO userDTO = modelMapper.map(user, UserForMyProfileDTO.class);
        userDTO.setTotalKarma(userService.findTotalKarmaByUserId(user.getId()));

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken
            (@Validated @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        logService.message("User controller, createAuthenticationToken() method called.", MessageType.INFO);

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping()
    public ResponseEntity<UserRegisterRequestDTO> createUser(@RequestBody @Validated UserRegisterRequestDTO newUser){

        logService.message("User controller, createUser() method called.", MessageType.INFO);

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserRegisterRequestDTO userDTO = modelMapper.map(createdUser, UserRegisterRequestDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping()
    @CrossOrigin
    public ResponseEntity<UserForMyProfileDTO> editUser(@RequestBody @Validated UserEditRequestDTO userEditRequestDTO,
                                                        Authentication authentication){

        logService.message("User controller, editUser() method called.", MessageType.INFO);

        if (authentication == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        User editedUser = userService.editUser(userEditRequestDTO, currentLoggedUser);

        UserForMyProfileDTO userDTO = modelMapper.map(editedUser, UserForMyProfileDTO.class);
        userDTO.setTotalKarma(userService.findTotalKarmaByUserId(editedUser.getId()));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/password")
    @CrossOrigin
    public ResponseEntity<UserGetAllResponseDTO> editPassword(@RequestBody @Validated UserEditPasswordRequestDTO userEditPasswordRequestDTO,
                                                          Authentication authentication){

        logService.message("User controller, editPassword() method called.", MessageType.INFO);

        if (authentication == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        User editedUserPassword = userService.editPassword(userEditPasswordRequestDTO, currentLoggedUser);

        if (editedUserPassword == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        UserGetAllResponseDTO userDTO = modelMapper.map(editedUserPassword, UserGetAllResponseDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
