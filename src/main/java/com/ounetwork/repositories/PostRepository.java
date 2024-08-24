/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;

import com.ounetwork.models.Post;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

/**
 *
 * @author Admin
 */
public interface PostRepository {
    List<Post> getAll();
    Post create(Post post);
    Long getNumberReactionById(String postId);
}
