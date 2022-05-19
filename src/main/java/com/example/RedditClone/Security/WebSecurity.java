package com.example.RedditClone.Security;

import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.ModeratorService;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebSecurity {

    private final ModeratorService moderatorService;
    private final UserService userService;

    public WebSecurity(UserService userService, ModeratorService moderatorService) {
        this.userService = userService;
        this.moderatorService = moderatorService;
    }

    public boolean amIAdminOrModerator(Authentication authentication, HttpServletRequest request, int id) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            return moderatorService.amIModerator(id, user.getId()) ||
                    userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean checkPostCreator(Authentication authentication, HttpServletRequest request, int idCommunity, int idPost) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());

            return moderatorService.amIModerator(idCommunity, user.getId()) ||
                    userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")) ||
                    userService.amIPostCreator(idPost, user.getId());

        } catch (ClassCastException e) {
            return false;
        }
    }

}
