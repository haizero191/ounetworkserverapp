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
import com.ounetwork.models.Role;
import com.ounetwork.services.RoleService;
import com.ounetwork.services.ValidationService;
import com.ounetwork.utils.JwtUtil;

import com.ounetwork.utils.ResponsePackage;
import com.ounetwork.validation.CustomException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/public/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/test")
    @Transactional
    public ResponsePackage index() throws JsonProcessingException {
        List<User> users = this.userService.getAllUsers();
        return new ResponsePackage(true, users, "Get all user success !", null);
    }

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

    @PostMapping(value = "/login", consumes = "application/json")
    @Transactional
    public ResponseEntity<ResponsePackage> login(@Valid @RequestBody Map<String, String> loginData) {

        String studentID = loginData.get("studentID");
        String password = loginData.get("password");

        try {
            User user = userService.login(studentID, password);
            String role = user.getRole().getName();
            boolean isApproved = user.getIsApproved();
            
            String token = jwtUtil.generateToken(studentID, role, isApproved);
            String username = jwtUtil.extractUsername(token);
            String roleName = jwtUtil.extractRole(token);
            
            
            Map<String, String> loginResponse = new HashMap<>();
            loginResponse.put("userId", user.getId());
            loginResponse.put("accessToken", token);


            return new ResponseEntity(new ResponsePackage(true, loginResponse, "Login success !", null), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ResponsePackage(false, null, "Login failed !", e.getErrors()), HttpStatus.UNAUTHORIZED);
        }
    }
}
