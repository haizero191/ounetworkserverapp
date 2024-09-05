/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Comment;

import com.ounetwork.repositories.CommentRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */

@Repository
public class CommentRepositoryImpl implements CommentRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public Comment create(Comment comment) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getCommentFormPost(String postId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = "SELECT r FROM Comment r WHERE r.postId = :postId";

        List<Comment> commentList = session.createQuery(hql, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
        return commentList;
    }
    
}
