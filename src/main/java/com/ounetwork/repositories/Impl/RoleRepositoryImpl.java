/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Role;
import com.ounetwork.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
/**
 *
 * @author Admin
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = "FROM Role WHERE name = :name";
        return session.createQuery(hql, Role.class)
                      .setParameter("name", name)
                      .uniqueResult();
    }
    
}
