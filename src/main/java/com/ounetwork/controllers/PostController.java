/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ounetwork.models.Post;
import com.ounetwork.models.User;
import com.ounetwork.models.Media;
import com.ounetwork.models.Reaction;
import com.ounetwork.models.Comment;
import com.ounetwork.services.CloudinaryService;
import com.ounetwork.services.CommentService;
import com.ounetwork.services.MediaService;
import com.ounetwork.services.PostService;
import com.ounetwork.services.ReactionService;
import com.ounetwork.services.UserService;
import com.ounetwork.utils.JwtUtil;
import com.ounetwork.utils.ResponsePackage;
import com.ounetwork.views.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/private/posts")
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PostService postService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private CommentService commentService;

    // ======================  GET ALL POST =========================== //
    @GetMapping("/get")
    @JsonView(View.Detailed.class)
    public ResponseEntity<ResponsePackage> index(
            HttpServletRequest request
    ) throws JsonProcessingException {
        List<Post> posts = this.postService.getAll();
        String jwtToken = (String) request.getAttribute("jwtToken");
        String studentId = jwtUtil.extractUsername(jwtToken);

        for (Post post : posts) {
            User user = this.userService.getUserByStudentID(studentId);

            // GET number reaction and comment
            Long postReactionCounter = this.postService.getNumberReactionById(post.getId());
            Long postCommentCounter = this.postService.getNumberCommentById(post.getId());
            Reaction UserInteractedPost = this.reactionService.isUserInteracted(user.getId(), post.getId());

            // SET number reaction and comment
            post.setReactionNumber(postReactionCounter);
            post.setCommentNumber(postCommentCounter);
            if (UserInteractedPost != null) {
                post.setIsUserInteracted(true);
                post.setReactionType(UserInteractedPost.getReactionType().toString());
                post.setUserInteraction(UserInteractedPost);
            } else {
                post.setIsUserInteracted(false);
            }
        }

        return new ResponseEntity<ResponsePackage>(new ResponsePackage(true, posts, "Get all post success !", null), HttpStatus.OK);
    }

    // ======================  CREATE NEW POST =========================== //
    @PostMapping(
            value = "/create",
            consumes = "multipart/form-data",
            produces = "application/json; charset=UTF-8"
    )
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponsePackage> create(
            HttpServletRequest request,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("content") String content
    ) throws JsonProcessingException {
        // Get JWT token and extract data
        String jwtToken = (String) request.getAttribute("jwtToken");
        String username = jwtUtil.extractUsername(jwtToken);

        // If not files in form response return 
        if (files.length == 0) {
            return new ResponseEntity(new ResponsePackage(false, null, "No file uploaded", null), HttpStatus.BAD_REQUEST);
        }

        // Handle upload mutilple file from response
        try {
            // Create new post with jwt authentication
            User userExist = this.userService.getUserByStudentID(username);
            Post newPost = new Post();
            newPost.setContent(content);
            newPost.setOwner(userExist);
            Post newPostCreatedResult = this.postService.create(newPost);

            // Upload image to cloudinary
            List<Map<String, String>> uploadedFilesResult = new ArrayList<>();
            uploadedFilesResult = this.cloudinaryService.uploadMutilFiles(files);
            List<Media> mediaListCreated = new ArrayList();
            // Create media with image upload 
            for (Map<String, String> file : uploadedFilesResult) {
                // Create new media
                Media newMedia = new Media();
                newMedia.setFileId(file.get("fileId"));
                newMedia.setFileType(file.get("fileType"));
                newMedia.setUrl(file.get("fileUrl"));

                // Get width and height of media
                newMedia.setWidth(file.get("width"));
                newMedia.setHeight(file.get("height"));
                newMedia.setPost(newPost);

                // Save media into database
                Media mediaUpdated = this.mediaService.create(newMedia);
                mediaListCreated.add(mediaUpdated);
            }
            // Add media list to new post
            newPostCreatedResult.setMediaList(mediaListCreated);
            return new ResponseEntity(new ResponsePackage(true, newPostCreatedResult, "Create post success !", null), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error throw", e.getMessage());
            return new ResponseEntity(new ResponsePackage(false, null, "Create post failed ! ", errors), HttpStatus.OK);
        }

    }

   
    // ======================  DELETE POST ================================ //
    @DeleteMapping("/delete/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        return ResponseEntity.noContent().build();
    }

    // ======================  REACTION TO POST =========================== //
    @PostMapping("/reaction/to/{postId}")
    public ResponseEntity<ResponsePackage> reactionToPost(
            HttpServletRequest request,
            @PathVariable String postId,
            @RequestParam(
                    name = "reactionType",
                    required = false,
                    defaultValue = "LOVE"
            ) Reaction.ReactionType reactionType
    ) {
        String jwtToken = (String) request.getAttribute("jwtToken");
        String studentId = jwtUtil.extractUsername(jwtToken);
        Map<String, String> result = new HashMap<String, String>();

        // Handle reaction status
        Boolean isReactionOrUnReaction = this.reactionService.reactionToPost(studentId, postId, reactionType);
        if (isReactionOrUnReaction) {
            result.put("status", "REACTION");
        } else {
            result.put("status", "UNREACTION");
        }

        // Add post id to reaction
        result.put("postId", postId);
        if (isReactionOrUnReaction) {
            return new ResponseEntity(new ResponsePackage(true, result, "Reaction to post success !", null), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new ResponsePackage(true, result, "UnReaction to post !", null), HttpStatus.OK);
        }
    }

    // ======================  COMMENT TO POST =========================== //
    @PostMapping("/comment/to/{postId}")
    @JsonView(View.Detailed.class)
    public ResponseEntity<ResponsePackage> commentToPost(
            HttpServletRequest request,
            @PathVariable String postId,
            @RequestParam(name = "content", required = true) String content
    ) {
        String jwtToken = (String) request.getAttribute("jwtToken");
        String studentId = jwtUtil.extractUsername(jwtToken);
        if (content == null) {
            return new ResponseEntity(new ResponsePackage(true, null, "Must be content require !", null), HttpStatus.BAD_REQUEST);
        }
        Comment commentResult = this.commentService.commentToPost(studentId, postId, content);
        return new ResponseEntity(new ResponsePackage(true, commentResult, "Comment to post success !", null), HttpStatus.OK);
    }
    
    // ======================  GET ALL COMMENT OF POST =========================== //
    @GetMapping("/comments/of/{postId}")
    @CrossOrigin
    @JsonView(View.Detailed.class)
    public ResponseEntity<ResponsePackage> view(
            @PathVariable String postId
    ) {
        List<Comment> commentList = this.commentService.getCommentFromPost(postId);
        return new ResponseEntity(new ResponsePackage(true, commentList, "get comment of post success !", null), HttpStatus.OK);
    }
}
