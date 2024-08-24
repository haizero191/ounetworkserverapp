/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.models.User;
import com.ounetwork.models.Role;
import com.ounetwork.models.Profile;
import com.ounetwork.repositories.ProfileRepository;
import com.ounetwork.repositories.RoleRepository;
import com.ounetwork.repositories.UserRepository;
import com.ounetwork.services.UserService;
import com.ounetwork.validation.CustomException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all user 
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // Handle Register
    @Override
    @Transactional
    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByName("USER");
        Map<String, String> errors = new HashMap<>();
        
        
        if (userRole != null) {
            user.setRole(userRole);
            user.setPassword(encodedPassword);
            Profile defProfile = new Profile();
            try {
                Profile userProfile = this.profileRepository.create(defProfile);
                if (userProfile != null) {
                    user.setProfile(defProfile);
                }
                return userRepository.create(user);
            } catch (Exception e) {
                errors.put("profile", "Profile have errors " + e.getMessage());
                throw new CustomException(errors);
            }

        } else {
            errors.put("role", "Can't find role with name [USER]");
            throw new CustomException(errors);
        }
    }

    // Upload avatar for user
    @Override
    @Transactional
    public User updateAvatar(String userId, String avatarUrl) {
        return userRepository.updateAvatar(userId, avatarUrl);
    }

    // Handle Login
    @Override
    @Transactional
    public User login(String studentID, String password) {
        Map<String, String> errors = new HashMap<>();
        User user = this.getUserByStudentID(studentID);

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

    // Get User by student ID
    @Override
    @Transactional
    public User getUserByStudentID(String studentId) {
        return this.userRepository.findByStudentID(studentId);
    }

    // Get User by user id
    @Override
    @Transactional
    public User getUserById(String userId) {
        return this.userRepository.findById(userId);
    }

    // Load user by username of UserDetail
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
        if (userRole.equals("TEACHER")) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (userRole.equals("ADMIN")) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            roles.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }

        // Trả về quyền hạn của người dùng, giả định rằng user.getRole() trả về vai trò như 'USER', 'ADMIN'
        return roles;
    }

}
