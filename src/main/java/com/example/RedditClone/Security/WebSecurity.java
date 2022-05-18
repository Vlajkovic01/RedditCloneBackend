package com.example.RedditClone.Security;

import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebSecurity {
    private final UserService userService;

    public WebSecurity(UserService userService) {
        this.userService = userService;
    }

    public boolean checkCommunityId(Authentication authentication, HttpServletRequest request, int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if(id == user.getId()) { //todo implement check for community id
            return true;
        }
        return false;
    }

}
