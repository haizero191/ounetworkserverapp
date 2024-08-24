/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Profile;
import com.ounetwork.repositories.ProfileRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */

@Repository
public class ProfileRepositoryImpl implements ProfileRepository{

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public Profile create(Profile profile) {
      Session session = sessionFactory.getObject().getCurrentSession();
      session.save(profile);
      return profile;
    }
    
}
