/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.validation;

import java.util.Map;

/**
 *
 * @author Admin
 */
public class CustomException extends RuntimeException {
    
    private final Map<String, String> errors;
    
    public CustomException(Map<String, String> errors) {
        super("Custom validation failed");
        this.errors = errors;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
}
