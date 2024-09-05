/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.User;
import com.ounetwork.models.Comment;
import com.ounetwork.repositories.CommentRepository;
import com.ounetwork.repositories.UserRepository;
import com.ounetwork.services.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment commentToPost(String commentorId, String postId, String content) {
        User user = this.userRepository.findByStudentID(commentorId);
        Comment newComment = new Comment();
        newComment.setContent(content);
        newComment.setUser(user);
        newComment.setPostId(postId);

        Comment createdComment = this.commentRepository.create(newComment);
        return createdComment;
    }

    @Override
    @Transactional
    public List<Comment> getCommentFromPost(String postId) {
        return this.commentRepository.getCommentFormPost(postId);
    }

}
