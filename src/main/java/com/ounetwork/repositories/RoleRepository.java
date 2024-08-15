/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;
import com.ounetwork.models.Role;
/**
 *
 * @author Admin
 */
public interface RoleRepository {
    Role findByName(String name);
}
