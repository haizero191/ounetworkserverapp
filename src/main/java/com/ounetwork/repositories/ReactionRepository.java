/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;

import java.util.List;
import com.ounetwork.models.Reaction;

/**
 *
 * @author Admin
 */
public interface ReactionRepository {
    List<Reaction> getReactionFormPost(String postId);
    
}
