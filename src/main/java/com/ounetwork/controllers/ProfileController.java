/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.views.View;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/private/profile")
@CrossOrigin
public class ProfileController {

    @GetMapping("/get")
    public String get() {
        return "Profile get api";
    }
    
    @PostMapping("/create")
    public String create() {
        return "Create profile api";
    }
    
    
}
