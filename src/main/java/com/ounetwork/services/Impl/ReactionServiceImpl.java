/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Reaction;
import com.ounetwork.repositories.ReactionRepository;
import com.ounetwork.services.ReactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    @Transactional
    public List<Reaction> getReactionFromPost(String postId) {
        return this.reactionRepository.getReactionFormPost(postId);
    }

}
