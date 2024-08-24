/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.ounetwork.services.UserService;
import com.ounetwork.utils.JwtUtil;
import com.ounetwork.utils.ResponsePackage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ounetwork.models.User;
import com.ounetwork.models.Profile;
import com.ounetwork.models.Spec;
import com.ounetwork.services.SpecService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecService specService;

    @GetMapping("/private/users/profile")
    @Transactional
    public String index() {
        return "get profile api";
    }

    @PostMapping(value = "/protected/users/profile/update", consumes = "multipart/form-data", produces = "application/json; charset=UTF-8")
    @Transactional
    @CrossOrigin
    public ResponseEntity<ResponsePackage> update(
            HttpServletRequest request,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "introduce", required = false) String introduce,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "spec", required = false) String spec,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "birthdate", required = false) String birthdate,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) throws IOException {

        String jwtToken = (String) request.getAttribute("jwtToken");
        String studentId = (String) request.getAttribute("studentId");
        User user = this.userService.getUserByStudentID(studentId);
        Map<String, String> errors = new HashMap<>();

        Profile updatedProfile = user.getProfile();
        if (updatedProfile != null) {
            updatedProfile.setFirstName(firstName);
            updatedProfile.setLastName(lastName);
            updatedProfile.setIntroduce(introduce);
            updatedProfile.setPhone(phone);
            updatedProfile.setAddress(address);
            updatedProfile.setBirthdate(birthdate);
            if (spec != null) {
                Spec specUpdate = this.specService.getSpecById(spec);
                if (specUpdate == null) {
                    errors.put("spec", "can't find this data");
                    return new ResponseEntity(new ResponsePackage(false, null, "Your profile failed !", errors), HttpStatus.BAD_REQUEST);
                } else {
                    updatedProfile.setSpec(specUpdate);
                }
            }

        }
        
        // Profile updated with profile 
        
        return new ResponseEntity(new ResponsePackage(true, updatedProfile, "Your profile updated", null), HttpStatus.OK);

//        if (jwtToken == null) {
//            return new ResponseEntity(new ResponsePackage(false, null, "Actions are not allowed. Please login before you take this action ... !", null), HttpStatus.BAD_REQUEST);
//        } else {
//            return new ResponseEntity(new ResponsePackage(true, null, "Your profile updated", null), HttpStatus.OK);
//        }
    }
}
