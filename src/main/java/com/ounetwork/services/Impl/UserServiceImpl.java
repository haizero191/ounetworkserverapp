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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional
    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByName("USER");
        if (userRole != null) {
            user.setRole(userRole);
            user.setPassword(encodedPassword);
            return userRepository.create(user);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("role", "Can't find role with name [USER]");
            throw new CustomException(errors);
        }
    }

    @Override
    @Transactional
    public User updateAvatar(String userId, String avatarUrl) {
        return userRepository.updateAvatar(userId, avatarUrl);
    }

    @Override
    @Transactional
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

    @Override
    public User getUserByStudentID(String studentId) {
        return this.userRepository.findByStudentID(studentId);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByStudentID(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getStudentID(), user.getPassword(), getAuthority(user));
    }
    
    // Get Authorization and create role for UserDetail
    private List<GrantedAuthority> getAuthority(User user) {
        String userRole = user.getRole().getName();

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + userRole));
        
        // Phân loại quyền hạn [ADMIN] > [TEACHER] > [USER]
        if(userRole.equals("TEACHER")) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        else if (userRole.equals("ADMIN")) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            roles.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }
        
        
        // Trả về quyền hạn của người dùng, giả định rằng user.getRole() trả về vai trò như 'USER', 'ADMIN'
        return roles;
    }
}
