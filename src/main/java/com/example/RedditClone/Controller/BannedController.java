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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

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

    @GetMapping("/community/{id}")
    public ResponseEntity<List<BannedGetAllDTO>> getBannedForCommunity(@PathVariable Integer id) {
        logService.message("Banned controller, getBannedForCommunity() method called.", MessageType.INFO);

        List<Banned> bans = bannedService.findAllByCommunityId(id);

        List<BannedGetAllDTO> bannedGetAllDTO = modelMapper.mapAll(bans, BannedGetAllDTO.class);
        return new ResponseEntity<>(bannedGetAllDTO, HttpStatus.OK);
    }

    @GetMapping("/community/{id}/user/{username}")
    public ResponseEntity<BannedGetAllDTO> isBanned(@PathVariable Integer id, @PathVariable String username) {
        logService.message("Banned controller, isBanned() method called.", MessageType.INFO);
        Banned banned = bannedService.findBannedByCommunityIdAndUserUsername(id, username);

        if (banned == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        BannedGetAllDTO bannedDTO = modelMapper.map(banned, BannedGetAllDTO.class);
        return new ResponseEntity<>(bannedDTO, HttpStatus.OK);
    }
}
