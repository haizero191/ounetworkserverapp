/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Reaction;
import com.ounetwork.repositories.ReactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
@Repository
public class ReactionRepositoryImpl implements ReactionRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Reaction> getReactionFormPost(String postId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = "SELECT r FROM Reaction r WHERE r.post.id = :postId";
        
        List<Reaction> reactionList = session.createQuery(hql, Reaction.class)
        .setParameter("postId", postId)
        .getResultList();
        
        return reactionList;
    }

}
