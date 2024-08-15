/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.User;
import com.ounetwork.models.Role;
import com.ounetwork.repositories.RoleRepository;
import com.ounetwork.repositories.UserRepository;
import com.ounetwork.services.UserService;
import com.ounetwork.validation.CustomException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByName("USER");
        if (userRole != null) {
            user.setRole(userRole);
            user.setPassword(encodedPassword);
            return userRepository.create(user);
        }
        else {
            Map<String, String> errors = new HashMap<>();
            errors.put("role", "Can't find role with name [USER]");
            throw new CustomException(errors);
        }
    }

    @Override
    public User updateAvatar(String userId, String avatarUrl) {
        return userRepository.updateAvatar(userId, avatarUrl);
    }

    @Override
    public User login(String studentID, String password) {
        Map<String, String> errors = new HashMap<>();
        User user = userRepository.findByStudentID(studentID);

        if (user == null) {
            errors.put("studentId", "Incorrect student ID");
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            errors.put("password", "Incorrect password");
        }

        if (!errors.isEmpty()) {
            throw new CustomException(errors);
        }

        return user;
    }
}
