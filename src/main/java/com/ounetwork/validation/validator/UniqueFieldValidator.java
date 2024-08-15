/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.validation.validator;

import com.ounetwork.services.ValidationService;
import com.ounetwork.validation.annotation.UniqueField;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */

@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    
    @Autowired
    private ValidationService validationService;
    
    private String table;
    private String field;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        this.table = constraintAnnotation.table();
        this.field = constraintAnnotation.field();
    }
    
    
  
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Có thể bỏ qua nếu giá trị null hoặc rỗng
        }
        
        try {
            boolean exists = validationService.isExistUniqueField(table, field, value);
            return exists;
        } catch (Exception e) {
            return false;
        }
    }

}
