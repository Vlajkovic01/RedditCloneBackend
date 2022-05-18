package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Request.UserRegisterRequestDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.CommunityService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/communities")
public class CommunityController {

    private final ExtendedModelMapper modelMapper;

    private final CommunityService communityService;

    public CommunityController(ExtendedModelMapper modelMapper, CommunityService communityService) {
        this.modelMapper = modelMapper;
        this.communityService = communityService;
    }

    @GetMapping
    public ResponseEntity<List<CommunityGetAllResponseDTO>> getCommunities() {

        List<Community> communities = communityService.findAll();

        List<CommunityGetAllResponseDTO> communitiesDTO = modelMapper.mapAll(communities, CommunityGetAllResponseDTO.class);

        return new ResponseEntity<>(communitiesDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommunityCreateRequestDTO> createCommunity(@RequestBody @Validated CommunityCreateRequestDTO newCommunity,
                                                                     Authentication authentication) {

        Community createdCommunity = communityService.createCommunity(newCommunity, authentication);

        if(createdCommunity == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        CommunityCreateRequestDTO communityDTO = modelMapper.map(createdCommunity, CommunityCreateRequestDTO.class);

        return new ResponseEntity<>(communityDTO, HttpStatus.CREATED);
    }
}
