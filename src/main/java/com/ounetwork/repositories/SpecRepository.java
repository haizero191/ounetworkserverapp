/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;
import com.ounetwork.models.Spec;
/**
 *
 * @author Admin
 */
public interface SpecRepository {
    Spec findById(String specId);
}
