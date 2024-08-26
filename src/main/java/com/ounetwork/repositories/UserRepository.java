/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;

import java.util.List;
import com.ounetwork.models.User;
import com.ounetwork.models.Profile;
/**
 *
 * @author Admin
 */
public interface UserRepository {
    List<User> getAllUsers();
    User create(User user);
    User findByStudentID(String studentID);
    User findById(String userId);
    User updateAvatar(String userId, String avatarUrl);
}
 