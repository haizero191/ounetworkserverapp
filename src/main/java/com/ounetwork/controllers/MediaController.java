/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.services.CloudinaryService;
import com.ounetwork.utils.ResponsePackage;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ounetwork.models.Media;
import com.ounetwork.services.MediaService;
import com.ounetwork.views.View;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/private/media")
public class MediaController {

    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    private MediaService mediaService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping(value = "/get")
    @JsonView(View.Summary.class)
    @CrossOrigin
    public List<Media> getall() {
        return this.mediaService.getAll();
    }

    @PostMapping(value = "/upload/files/{userId}", consumes = "multipart/form-data")
    @CrossOrigin
    public ResponseEntity uploadFiles(@RequestParam("files") MultipartFile[] files) {
        Object uploadedFilesResult = new ArrayList<>();

        // If not files in form response return 
        if (files.length == 0) {
            return new ResponseEntity(new ResponsePackage(false, null, "No file uploaded", null), HttpStatus.BAD_REQUEST);
        }

        // Handle upload mutilple file from response
        try {
            uploadedFilesResult = this.cloudinaryService.uploadMutilFiles(files);
            return new ResponseEntity(new ResponsePackage(true, uploadedFilesResult, "Upload your files success", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponsePackage(false, null, "Upload your files failed", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
