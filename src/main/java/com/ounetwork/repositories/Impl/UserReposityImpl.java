/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Profile;
import com.ounetwork.models.User;
import com.ounetwork.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
public class UserReposityImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    // Get All user
    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getObject().getCurrentSession();
        List<User> users = session.createQuery("from User", User.class).list();
        return users;
    }

    // Create a new user 
    @Override
    public User create(User user) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(user);
        return user;
    }

    // Update avatar for user 
    @Override
    public User updateAvatar(String userId, String avatarUrl) {
        Session session = sessionFactory.getObject().getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            session.update(user);
            return user;
        } else {
            return null;
        }
    }

    // Find user by id
    @Override
    public User findById(String userId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(User.class, userId);
    }

    //Find user by student id
    @Override
    public User findByStudentID(String studentID) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = "FROM User WHERE studentID = :studentID ";
        return session.createQuery(hql, User.class)
                .setParameter("studentID", studentID)
                .uniqueResult();
    }

    
}
