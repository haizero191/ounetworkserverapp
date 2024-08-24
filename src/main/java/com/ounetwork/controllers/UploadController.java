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
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/private/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(value = "/files/{userId}", consumes = "multipart/form-data")
    public ResponseEntity uploadFiles(@RequestParam("files") MultipartFile[] files) {
        Object uploadedFileUrls = new ArrayList<>();
        
        // If not files in form response return 
        if (files.length == 0) {
            return new ResponseEntity(new ResponsePackage(false, null, "No file uploaded", null), HttpStatus.BAD_REQUEST);
        }

        // Handle upload mutilple file from response
        try {
            uploadedFileUrls = this.cloudinaryService.uploadMutilFiles(files);
            return new ResponseEntity(new ResponsePackage(true, uploadedFileUrls, "Upload your files success", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponsePackage(false, null, "Upload your files failed", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
