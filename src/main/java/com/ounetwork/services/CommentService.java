/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;
import com.ounetwork.models.Comment;
import java.util.List;
/**
 *
 * @author Admin
 */
public interface CommentService {
    Comment commentToPost(String commentor, String postId, String content);
    List<Comment> getCommentFromPost(String postId);
}
