/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;

/**
 *
 * @author Admin
 */
public interface ValidationRepository {
    boolean isExistUniqueField(String tableName, String fieldName, String value);
}
