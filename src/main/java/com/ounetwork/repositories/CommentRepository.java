/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;
import com.ounetwork.models.Comment;

import java.util.List;
/**
 *
 * @author Admin
 */
public interface CommentRepository {
    Comment create(Comment comment);
    List<Comment> getCommentFormPost(String postId);
}
