package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Banned.Request.BannedCreateDTO;
import com.example.RedditClone.Model.DTO.Banned.Response.BannedGetAllDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Banned;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Service.BannedService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping(value = "api/banned")
public class BannedController {
    private final ExtendedModelMapper modelMapper;
    private final BannedService bannedService;
    private final LogService logService;

    public BannedController(LogService logService, BannedService bannedService, ExtendedModelMapper modelMapper) {
        this.logService = logService;
        this.bannedService = bannedService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<BannedGetAllDTO> banUser(@RequestBody BannedCreateDTO createBan,
                                                                     Authentication authentication) {
        logService.message("Banned controller, banUser() method called.", MessageType.INFO);

        Banned createdBan = bannedService.createBan(createBan, authentication);

        if(createdBan == null){
            logService.message("Banned controller, createBan() method, failed to create a ban.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        BannedGetAllDTO bannedGetAllDTO = modelMapper.map(createdBan, BannedGetAllDTO.class);
        return new ResponseEntity<>(bannedGetAllDTO, HttpStatus.CREATED);
    }
}
