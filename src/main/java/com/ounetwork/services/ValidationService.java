/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;

/**
 *
 * @author Admin
 */
public interface ValidationService {
    boolean isExistUniqueField(String tableName, String fieldName, String value);
}
