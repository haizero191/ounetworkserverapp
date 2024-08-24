/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Post;

import com.ounetwork.repositories.PostRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    // Get all post
    @Override
    public List<Post> getAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        String jpql = "SELECT p FROM Post p LEFT JOIN FETCH p.mediaList ORDER BY p.createdAt DESC";
        TypedQuery<Post> query = entityManager.createQuery(jpql, Post.class);
        return query.getResultList();
    }

    @Override
    public Post create(Post post) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(post);
        return post;
    }

    @Override
    public Long getNumberReactionById(String postId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = "SELECT COUNT(r) FROM Reaction r WHERE r.post.id = :postId";
        Long reactionCounter = session.createQuery(hql, Long.class)
                .setParameter("postId", postId)
                .getSingleResult();
        return reactionCounter;
    }
}
