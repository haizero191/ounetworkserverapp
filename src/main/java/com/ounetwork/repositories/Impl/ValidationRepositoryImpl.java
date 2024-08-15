/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.repositories.ValidationRepository;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */

@Repository
public class ValidationRepositoryImpl implements ValidationRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public boolean isExistUniqueField(String tableName, String fieldName, String value) {
        
        Session session = sessionFactory.getObject().getCurrentSession();
        String hql = String.format("SELECT COUNT(*) FROM %s WHERE %s = :value", tableName, fieldName);
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("value", value);
        Long count = query.uniqueResult();

        return count != null && count == 0;
    }

}
