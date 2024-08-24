/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.Spec;
import com.ounetwork.repositories.SpecRepository;
import com.ounetwork.services.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecRepository specRepository;
    
    @Override
    public Spec getSpecById(String specId) {
        return this.specRepository.findById(specId);
    }
    
}
