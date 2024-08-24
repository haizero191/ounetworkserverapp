/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.repositories.Impl;

import com.ounetwork.models.Media;
import com.ounetwork.repositories.MediaRepository;
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
public class MediaRepositoryImpl implements MediaRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Media> getAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        List<Media> mediaResult = session.createQuery("from Media", Media.class).list();
        return mediaResult;
    }

    @Override
    public Media creare(Media media) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(media);
        return media;
    }

}
