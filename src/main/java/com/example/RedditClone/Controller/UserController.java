package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserEditPasswordRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserEditRequestDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserForMyProfileDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserLoginRequestDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserTokenState;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Security.TokenUtils;
import com.example.RedditClone.Service.ModeratorService;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    private ModeratorService moderatorService;
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ExtendedModelMapper modelMapper;
    private final UserService userService;

    public UserController(UserService userService, ExtendedModelMapper modelMapper, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ModeratorService moderatorService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.moderatorService = moderatorService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<UserGetAllResponseDTO>> getUsers() {

        List<User> users = userService.findAll();

        List<UserGetAllResponseDTO> usersDTO = modelMapper.mapAll(users, UserGetAllResponseDTO.class);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserForMyProfileDTO> getUser(@PathVariable String username) {

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
