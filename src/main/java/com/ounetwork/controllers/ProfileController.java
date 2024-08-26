/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.models.User;
import com.ounetwork.services.UserService;
import com.ounetwork.views.View;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ounetwork.models.Profile;
import com.ounetwork.services.ProfileService;
import com.ounetwork.utils.ResponsePackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/public/profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/get/{studentID}")
    public ResponseEntity<ResponsePackage> get(HttpServletRequest request, @PathVariable("studentID") String studentID) {
        Profile profile = this.profileService.getByStudentId(studentID);
        return new ResponseEntity<ResponsePackage>(new ResponsePackage(true, profile, "Get all post success !", null), HttpStatus.OK);
    }
}
