/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;

import com.ounetwork.models.User;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserService {

    List<User> getAllUsers();

    User register(User user);

    User login(String studentID, String password);

    User updateAvatar(String userId, String avatarUrl);

}
