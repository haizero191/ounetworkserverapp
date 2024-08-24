/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Spec;
import com.ounetwork.repositories.SpecRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */

@Repository
public class SpecRepositoryImpl implements SpecRepository{

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public Spec findById(String specId) {
       Session session = sessionFactory.getObject().getCurrentSession();
       return session.get(Spec.class, specId);
    }
}
