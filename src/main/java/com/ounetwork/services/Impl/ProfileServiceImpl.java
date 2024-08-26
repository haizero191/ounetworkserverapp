/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Profile;
import com.ounetwork.repositories.ProfileRepository;
import com.ounetwork.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    @Transactional
    public Profile create(Profile profile) {
        return this.profileRepository.create(profile);
    }

    @Override
    @Transactional
    public Profile updated(Profile profile) {
        return this.profileRepository.update(profile);
    }

    @Override
    @Transactional
    public Profile getByStudentId(String studentID) {
        return this.profileRepository.findByStudentId(studentID);
    }

}
