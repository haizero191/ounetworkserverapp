/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Post;
import com.ounetwork.models.User;
import com.ounetwork.repositories.PostRepository;
import com.ounetwork.services.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    // Get All post
    @Override
    @Transactional
    public List<Post> getAll() {
        return this.postRepository.getAll();
    }

    // Create new post
    @Override
    @Transactional
    public Post create(Post post) {
        return this.postRepository.create(post);
    }

    // Get number of reaction
    @Override
    @Transactional
    public Long getNumberReactionById(String postId) {
        return this.postRepository.getNumberReactionById(postId);
    }
    
    // Get number of comment
    @Override
    @Transactional
    public Long getNumberCommentById(String postId) {
        return this.postRepository.getNumberCommentById(postId);
    }

    @Override
    @Transactional
    public Post getPostById(String postId) {
        return this.postRepository.findById(postId);
    }

}
