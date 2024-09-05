/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Reaction;
import com.ounetwork.models.User;
import com.ounetwork.repositories.PostRepository;
import com.ounetwork.repositories.ReactionRepository;
import com.ounetwork.repositories.UserRepository;
import com.ounetwork.services.ReactionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class ReactionServiceImpl implements ReactionService {

    private static final Logger logger = LoggerFactory.getLogger(ReactionServiceImpl.class);
    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<Reaction> getReactionFromPost(String postId) {
        return this.reactionRepository.getReactionFormPost(postId);
    }

    @Override
    @Transactional
    public Boolean reactionToPost(String reactorId, String postId, Reaction.ReactionType reactionType) {
        User user = this.userRepository.findByStudentID(reactorId);
        Reaction reactionExist = this.reactionRepository.isExist(user.getId(), postId);

        if (reactionExist != null) {
            logger.debug("updated reaction checking " + (reactionExist.getReactionType() != reactionType));
            if (reactionExist.getReactionType() != reactionType) {
                // update reaction if user change emotion
                reactionExist.setReactionType(reactionType);
                this.reactionRepository.update(reactionExist);
                logger.debug("updated reaction");
                return true;
            } else {
                // Delete reaction if reaction was exist
                this.reactionRepository.delete(reactionExist.getId());
                return false;
            }
        } else {
            Reaction newReaction = new Reaction();
            newReaction.setPostId(postId);
            newReaction.setUser(user);
            newReaction.setReactionType(reactionType);
            this.reactionRepository.create(newReaction);
            return true;
        }
    }

    @Override
    @Transactional
    public Reaction deleteById(String reactId) {
        return this.reactionRepository.delete(reactId);
    }

    @Override
    @Transactional
    public Reaction isUserInteracted(String reactorId, String postId) {
        if(this.reactionRepository.isExist(reactorId, postId) != null)
            return this.reactionRepository.isExist(reactorId, postId);
        else return null;
    }

    @Override
    public Boolean updateToPost(String reactorId, String postId, Reaction.ReactionType reactionType) {
        User user = this.userRepository.findByStudentID(reactorId);
        Reaction reactionExist = this.reactionRepository.isExist(user.getId(), postId);

        if (reactionExist != null) {
            if (reactionExist.getReactionType() != reactionType) {
                // update reaction if user change emotion
                reactionExist.setReactionType(reactionType);
                this.reactionRepository.update(reactionExist);
                return true;
            } else {
                // Delete reaction if reaction was exist
                return false;
            }
        } else {
            Reaction newReaction = new Reaction();
            newReaction.setPostId(postId);
            newReaction.setUser(user);
            newReaction.setReactionType(reactionType);
            this.reactionRepository.create(newReaction);
            return true;
        }
    }

}
