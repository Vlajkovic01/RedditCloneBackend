package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.CommunityService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
