/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.repositories.UserRepository;
import com.ounetwork.repositories.ValidationRepository;
import com.ounetwork.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */

@Service
public class ValidationServiceImpl implements ValidationService{
    @Autowired
    private ValidationRepository validationRepository;
    
    @Override
    public boolean isExistUniqueField(String tableName, String fieldName, String value) {
         return validationRepository.isExistUniqueField(tableName, fieldName, value);
    }
    
}
