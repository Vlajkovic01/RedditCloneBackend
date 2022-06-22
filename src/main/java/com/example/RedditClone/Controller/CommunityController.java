package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Community.Request.CommunityCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunityEditRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Request.CommunitySuspendRequestDTO;
import com.example.RedditClone.Model.DTO.Community.Response.CommunityGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Request.PostEditRequestDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetAllResponseDTO;
import com.example.RedditClone.Model.DTO.Post.Response.PostGetForCommunityDTO;
import com.example.RedditClone.Model.DTO.Report.Response.ReportGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Community;
import com.example.RedditClone.Model.Entity.Post;
import com.example.RedditClone.Model.Entity.Report;
import com.example.RedditClone.Repository.ModeratorRepository;
import com.example.RedditClone.Repository.PostRepository;
import com.example.RedditClone.Service.*;
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
import java.util.Set;

@RestController
@RequestMapping(value = "api/communities")
public class CommunityController {
    private final ReportService reportService;
    private final LogService logService;
    private final ModeratorRepository moderatorRepository;
    private final PostRepository postRepository;
    private final ReactionService reactionService;
    private final PostService postService;
    private final UserService userService;
    private final RuleService ruleService;

    private final ExtendedModelMapper modelMapper;

    private final CommunityService communityService;

    public CommunityController(ExtendedModelMapper modelMapper, CommunityService communityService, RuleService ruleService, UserService userService, PostService postService, ReactionService reactionService, PostRepository postRepository, ModeratorRepository moderatorRepository, LogService logService, ReportService reportService) {
        this.modelMapper = modelMapper;
        this.communityService = communityService;
        this.ruleService = ruleService;
        this.userService = userService;
        this.postService = postService;
        this.reactionService = reactionService;
        this.postRepository = postRepository;
        this.moderatorRepository = moderatorRepository;
        this.logService = logService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<CommunityGetAllResponseDTO>> getCommunities() {

        logService.message("Community controller, getCommunities() method called.", MessageType.INFO);

        List<Community> communities = communityService.find12RandomCommunities();

        List<CommunityGetAllResponseDTO> communitiesDTO = modelMapper.mapAll(communities, CommunityGetAllResponseDTO.class);

        return new ResponseEntity<>(communitiesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityGetAllResponseDTO> getSingleCommunity(@PathVariable Integer id) {

        logService.message("Community controller, getSingleCommunity() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(id);
        if (community == null) {
            logService.message("Community controller, getSingleCommunity() method, failed to find a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CommunityGetAllResponseDTO communityDTO = modelMapper.map(community, CommunityGetAllResponseDTO.class);

        return new ResponseEntity<>(communityDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommunityCreateRequestDTO> createCommunity(@RequestBody @Validated CommunityCreateRequestDTO newCommunity,
                                                                     Authentication authentication) {

        logService.message("Community controller, createCommunity() method called.", MessageType.INFO);

        Community createdCommunity = communityService.createCommunity(newCommunity, authentication);

        if(createdCommunity == null){
            logService.message("Community controller, createCommunity() method, failed to create a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        CommunityCreateRequestDTO communityDTO = modelMapper.map(createdCommunity, CommunityCreateRequestDTO.class);
        communityDTO.setRules(new HashSet<>(ruleService.findAllByCommunity(createdCommunity)));
        return new ResponseEntity<>(communityDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<CommunityGetAllResponseDTO> editCommunity(@RequestBody @Validated CommunityEditRequestDTO communityEditRequestDTO,
                                                                 @PathVariable Integer id) {

        logService.message("Community controller, editCommunity() method called.", MessageType.INFO);

        Community communityForEdit = communityService.findCommunityById(id);

        if (communityForEdit == null) {
            logService.message("Community controller, editCommunity() method, failed to find a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        Community editedCommunity = communityService.editCommunity(communityEditRequestDTO, communityForEdit);

        CommunityGetAllResponseDTO communityDTO = modelMapper.map(editedCommunity, CommunityGetAllResponseDTO.class);

        return new ResponseEntity<>(communityDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/posts")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<PostGetAllResponseDTO> createPost(@RequestBody @Validated PostCreateRequestDTO newPost,
                                                            Authentication authentication, @PathVariable Integer id) {

        logService.message("Community controller, createPost() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(id);
        Post createdPost = postService.createPost(newPost, authentication, community);

        if(community == null || createdPost == null){
            logService.message("Community controller, createPost() method, failed to create a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        community.getPosts().add(createdPost);
        communityService.save(community);
        PostGetAllResponseDTO postDTO = modelMapper.map(createdPost, PostGetAllResponseDTO.class);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idCommunity}/posts/{idPost}")
    @CrossOrigin
    public ResponseEntity<PostGetAllResponseDTO> editPost(@RequestBody @Validated PostEditRequestDTO postEditRequestDTO,
                                                                 @PathVariable Integer idCommunity,
                                                                 @PathVariable Integer idPost) {

        logService.message("Community controller, editPost() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(idCommunity);
        Post postForEdit = postService.findPostById(idPost);
        Post editedPost = postService.editPost(postEditRequestDTO, postForEdit);

        if(community == null || postForEdit == null || editedPost == null){
            logService.message("Community controller, editPost() method, failed to edit a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        PostGetAllResponseDTO postDTO = modelMapper.map(editedPost, PostGetAllResponseDTO.class);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idCommunity}/posts/{idPost}")
    @CrossOrigin()
    public ResponseEntity deletePost(@PathVariable Integer idCommunity,
                                                          @PathVariable Integer idPost) {

        logService.message("Community controller, deletePost() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(idCommunity);
        Post postForDelete = postService.findPostById(idPost);

        if(community == null || postForDelete == null){
            logService.message("Community controller, deletePost() method, failed to delete a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        postForDelete.setCommunity(null);
        postRepository.save(postForDelete);
        postRepository.delete(postForDelete);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/{id}/suspend")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<CommunityEditRequestDTO> suspendCommunity(@RequestBody @Validated CommunitySuspendRequestDTO communitySuspendRequestDTO,
                                                                        @PathVariable Integer id) {

        logService.message("Community controller, suspendCommunity() method called.", MessageType.INFO);

        Community communityForDelete = communityService.findCommunityById(id);

        if (communityForDelete == null) {
            logService.message("Community controller, suspendCommunity() method, failed to suspend a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        communityService.suspendCommunity(communityForDelete, communitySuspendRequestDTO);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{idCommunity}/posts/{idPost}")
    public ResponseEntity<PostGetAllResponseDTO> getPostFromCommunity(@PathVariable Integer idCommunity,
                                                                        @PathVariable Integer idPost) {

        logService.message("Community controller, getPostFromCommunity() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(idCommunity);
        Post post = postService.findPostById(idPost);

        if(community == null || post == null){
            logService.message("Community controller, getPostFromCommunity() method, failed to find a post.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        PostGetAllResponseDTO postDTO = modelMapper.map(post, PostGetAllResponseDTO.class);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/posts/new")
    public ResponseEntity<List<PostGetForCommunityDTO>> getNewPostsSort(@PathVariable Integer id) {

        logService.message("Community controller, getNewPostsSort() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(id);

        if(community == null){
            logService.message("Community controller, getNewPostsSort() method, failed to find a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Post> posts = postService.newSortInCommunity(community);

        List<PostGetForCommunityDTO> postsDTO = modelMapper.mapAll(posts, PostGetForCommunityDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/posts/top")
    public ResponseEntity<List<PostGetForCommunityDTO>> getTopPostsSort(@PathVariable Integer id) {

        logService.message("Community controller, getTopPostsSort() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(id);

        if(community == null){
            logService.message("Community controller, getTopPostsSort() method, failed to find a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Set<Post> posts = postService.topSortInCommunity(community);

        List<PostGetForCommunityDTO> postsDTO = modelMapper.mapAll(posts.stream().toList(), PostGetForCommunityDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/posts/hot")
    public ResponseEntity<List<PostGetForCommunityDTO>> getHotPostsSort(@PathVariable Integer id) {

        logService.message("Community controller, getHotPostsSort() method called.", MessageType.INFO);

        Community community = communityService.findCommunityById(id);

        if(community == null){
            logService.message("Community controller, getHotPostsSort() method, failed to find a community.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Set<Post> posts = postService.hotSortInCommunity(community);

        List<PostGetForCommunityDTO> postsDTO = modelMapper.mapAll(posts.stream().toList(), PostGetForCommunityDTO.class);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<List<ReportGetAllResponseDTO>> getReports(@PathVariable Integer id) {

        logService.message("Community controller, getReports() method called.", MessageType.INFO);

        List<Report> reports = reportService.findAllByCommunityId(id);

        List<ReportGetAllResponseDTO> reportsDTO = modelMapper.mapAll(reports, ReportGetAllResponseDTO.class);

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }
}
