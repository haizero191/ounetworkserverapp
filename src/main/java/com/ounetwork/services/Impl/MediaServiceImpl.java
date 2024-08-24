/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Media;
import com.ounetwork.repositories.MediaRepository;
import com.ounetwork.services.MediaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    @Transactional
    public List<Media> getAll() {
        return this.mediaRepository.getAll();
    }

    @Override
    @Transactional
    public Media create(Media media) {
        return this.mediaRepository.creare(media);
    }
}
