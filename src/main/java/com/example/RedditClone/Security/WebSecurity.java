package com.example.RedditClone.Security;

import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.ModeratorService;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    public boolean checkCommunityId(Authentication authentication, HttpServletRequest request, int id) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());

            return moderatorService.amIModerator(id, user.getId());
        } catch (ClassCastException e) {
            return false;
        }
    }

}
