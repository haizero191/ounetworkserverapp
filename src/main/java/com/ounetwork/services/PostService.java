/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;

import com.ounetwork.models.Post;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface PostService {
    List<Post> getAll();
    Post create(Post post);
    Long getNumberReactionById(String postId);
}
