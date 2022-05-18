package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunityEditRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.User.Response.UserGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.User;
import com.example.RedditClone.Service.CommunityService;
import com.example.RedditClone.Service.PostService;
import com.example.RedditClone.Service.RuleService;
import com.example.RedditClone.Service.UserService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "api/communities")
public class CommunityController {

    private final PostService postService;
    private final UserService userService;
    private final RuleService ruleService;

    private final ExtendedModelMapper modelMapper;

    private final CommunityService communityService;

    public CommunityController(ExtendedModelMapper modelMapper, CommunityService communityService, RuleService ruleService, UserService userService, PostService postService) {
        this.modelMapper = modelMapper;
        this.communityService = communityService;
        this.ruleService = ruleService;
        this.userService = userService;
        this.postService = postService;
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
        communityDTO.setRules(new HashSet<>(ruleService.findAllByCommunity(createdCommunity)));
        return new ResponseEntity<>(communityDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityEditRequestDTO> editCommunity(@RequestBody @Validated CommunityEditRequestDTO communityEditRequestDTO,
                                                                 @PathVariable Integer id) {
        Community communityForEdit = communityService.findCommunityById(id);

        if (communityForEdit == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        Community editedCommunity = communityService.editCommunity(communityEditRequestDTO, communityForEdit);

        CommunityEditRequestDTO communityDTO = modelMapper.map(editedCommunity, CommunityEditRequestDTO.class);

        return new ResponseEntity<>(communityDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/posts")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<PostGetAllResponseDTO> createPost(@RequestBody @Validated PostCreateRequestDTO newPost,
                                                            Authentication authentication, @PathVariable Integer id) {

        Post createdPost = postService.createPost(newPost, authentication);
        Community community = communityService.findCommunityById(id);

        if(community == null || createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        community.getPosts().add(createdPost);
        PostGetAllResponseDTO postDTO = modelMapper.map(createdPost, PostGetAllResponseDTO.class);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idCommunity}/posts/{idPost}")
    public ResponseEntity<PostGetAllResponseDTO> editPost(@RequestBody @Validated PostEditRequestDTO postEditRequestDTO,
                                                                 @PathVariable Integer idCommunity,
                                                                 @PathVariable Integer idPost) {
        Community community = communityService.findCommunityById(idCommunity);
        Post postForEdit = postService.findPostById(idPost);
        Post editedPost = postService.editPost(postEditRequestDTO, postForEdit);

        if(community == null || postForEdit == null || editedPost == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        PostGetAllResponseDTO postDTO = modelMapper.map(editedPost, PostGetAllResponseDTO.class);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }
}
