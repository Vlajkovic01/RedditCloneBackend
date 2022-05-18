package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.User.Request.UserEditRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserLoginRequestDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserTokenState;
import com.example.RedditClone.Security.TokenUtils;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ExtendedModelMapper modelMapper;
    private final UserService userService;

    public UserController(UserService userService, ExtendedModelMapper modelMapper, AuthenticationManager authenticationManager, TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<UserGetAllResponseDTO>> getUsers() {

        List<User> users = userService.findAll();

        List<UserGetAllResponseDTO> usersDTO = modelMapper.mapAll(users, UserGetAllResponseDTO.class);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken
            (@Validated @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

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

    @PostMapping("/registration")
    public ResponseEntity<UserRegisterRequestDTO> createUser(@RequestBody @Validated UserRegisterRequestDTO newUser){

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserRegisterRequestDTO userDTO = modelMapper.map(createdUser, UserRegisterRequestDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UserGetAllResponseDTO> editUser(@RequestBody @Validated UserEditRequestDTO userEditRequestDTO,
                                                        Authentication authentication){
        if (authentication == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentLoggedUser = userService.findByUsername(userDetails.getUsername());

        if (currentLoggedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        User editedUser = userService.editUser(userEditRequestDTO, currentLoggedUser);

        UserGetAllResponseDTO userDTO = modelMapper.map(editedUser, UserGetAllResponseDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
