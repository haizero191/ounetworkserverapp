/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ounetwork.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ounetwork.models.User;
import com.ounetwork.services.RoleService;
import com.ounetwork.services.ValidationService;
import com.ounetwork.utils.JwtUtil;
import com.ounetwork.utils.ResponsePackage;
import com.ounetwork.validation.CustomException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/public/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtUtil jwtUtil;

    // AUTHENTICATION - TEST CONTROLLER
    @GetMapping("/test")
    @Transactional
    public ResponsePackage index() throws JsonProcessingException {
        List<User> users = this.userService.getAllUsers();
        return new ResponsePackage(true, users, "Get all user success !", null);
    }

    
    // AUTHENTICATION - REGISTER NEW USER
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ResponsePackage> register(@Valid @RequestBody User user, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }

        if (!validationService.isExistUniqueField("User", "studentID", user.getStudentID())) {
            errors.put("studentID", "Student ID number has been registered and waiting for verification");
        }

        if (!validationService.isExistUniqueField("User", "email", user.getEmail())) {
            errors.put("email", "Email has been registered and waiting for verification");
        }

        if (!errors.isEmpty()) {
            throw new CustomException(errors);
        }

        try {
            User newUser = this.userService.register(user);
            return new ResponseEntity(new ResponsePackage(true, newUser, "Register new user success !", null), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity(new ResponsePackage(true, null, "Register failed !", e.getErrors()), HttpStatus.NOT_FOUND);
        }
    }

    
    // AUTHENTICATION - LOGIN USER
    @PostMapping(value = "/login", consumes = "application/json")
    @Transactional
    public ResponseEntity<ResponsePackage> login(@Valid @RequestBody Map<String, String> loginData) {
        String studentID = loginData.get("studentID");
        String password = loginData.get("password");
        
        User user = this.userService.login(studentID, password);
        String role = user.getRole().getName();
        boolean isApproved = user.getIsApproved();

        // Create Token
        String token = jwtUtil.generateToken(studentID, role, isApproved);
        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("accessToken", token);
        loginResponse.put("user", user);

        return new ResponseEntity(new ResponsePackage(true, loginResponse, "Login success !", null), HttpStatus.OK);
    }
}
