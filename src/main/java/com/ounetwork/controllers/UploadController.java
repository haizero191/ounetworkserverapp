/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.ounetwork.services.CloudinaryService;
import com.ounetwork.services.UserService;
import com.ounetwork.utils.ResponsePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ounetwork.models.User;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload-avatar/{userId}", consumes = "multipart/form-data")
    public ResponsePackage uploadAvatar(@PathVariable String userId, @RequestParam("avatar") MultipartFile avatar) {
        if (avatar.isEmpty()) {
            return new ResponsePackage(false, null, "No file uploaded", null);
        }
        try {
            // Upload avatar to Cloudinary
            String avatarUrl = cloudinaryService.uploadFile(avatar);
            // Update user avatar URL
            User user = userService.updateAvatar(userId, avatarUrl);
            return new ResponsePackage(true, avatarUrl, "Avatar uploaded successfully!", null);
        } catch (Exception e) {
            return new ResponsePackage(false, null, "Failed to upload avatar: " + e.getMessage(), null);
        }
    }
}
