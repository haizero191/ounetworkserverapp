/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Reaction;
import com.ounetwork.repositories.ReactionRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Admin
 */
@Repository
public class ReactionRepositoryImpl implements ReactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReactionRepositoryImpl.class);

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

    @Override
    public Reaction create(Reaction reaction) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(reaction);
        return reaction;
    }

    @Override
    public Reaction delete(String reactionId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Reaction reaction = session.get(Reaction.class, reactionId);
        if (reaction != null) {
            session.delete(reaction);
        } else {
            throw new EntityNotFoundException("Reaction not found with id " + reactionId);
        }
        return reaction;
    }

    @Override
    public Reaction findById(String reactionId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Reaction.class, reactionId);
    }

    @Override
    public Boolean deleteExist(String reactorId, String postId) {
        Session session = sessionFactory.getObject().getCurrentSession();

        // Tạo truy vấn để tìm kiếm Reaction
        Query<Reaction> query = session.createQuery("FROM Reaction WHERE userId = :userId AND postId = :postId", Reaction.class);
        query.setParameter("userId", reactorId);
        query.setParameter("postId", postId);
        query.setFirstResult(0);
        query.setMaxResults(1);

        Reaction reaction = query.uniqueResult();

        // Nếu tìm thấy, xóa phản ứng này
        if (reaction != null) {
            session.delete(reaction);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Reaction isExist(String reactorId, String postId) {
        Session session = sessionFactory.getObject().getCurrentSession();

        // Tạo truy vấn để tìm kiếm Reaction
        Query<Reaction> query = session.createQuery("FROM Reaction WHERE userId = :userId AND postId = :postId", Reaction.class);
        query.setParameter("userId", reactorId);
        query.setParameter("postId", postId);
        query.setFirstResult(0);
        query.setMaxResults(1);

        Reaction reaction = query.uniqueResult();
        // Nếu tìm thấy, xóa phản ứng này
        if (reaction != null) {
            return reaction;
        } else {
            return null;
        }
    }

    @Override
    public Reaction update(Reaction reaction) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.update(reaction);
        return reaction;
    }

}
