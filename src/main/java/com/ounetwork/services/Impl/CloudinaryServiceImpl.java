/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.services.Impl;

import com.ounetwork.services.CloudinaryService;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ounetwork.controllers.MediaController;
import com.ounetwork.utils.ResponsePackage;
import com.ounetwork.validation.CustomException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Handle upload to cloudinary
    @Override
    public List<Map<String, String>> uploadMutilFiles(MultipartFile[] files) throws IOException {
        List<Map<String, String>> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        // Handle uplod file to cloudinary
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                //Upload each file to Cloudinary and add the URL to the list
                try {
                    // Upload file to cloudinary
                    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "ounetworkresources"));
                    String fileUrl = (String) uploadResult.get("secure_url");
                    String fileId = (String) uploadResult.get("public_id");
                    String fileSize = String.valueOf(uploadResult.get("bytes"));

                    Long width = (Long) uploadResult.get("width");
                    Long height = (Long) uploadResult.get("height");

                    logger.debug("size image: " + width + " " + height);

                    // Create a map with fileId and fileUrl and add it to the list
                    Map<String, String> fileData = new HashMap<>();
                    fileData.put("fileId", fileId);
                    fileData.put("fileUrl", fileUrl);
                    fileData.put("fileSize", fileSize);
                    // Get size of media
                    fileData.put("width", String.valueOf(width));
                    fileData.put("height", String.valueOf(height));
                    // Add file to list
                    uploadedFiles.add(fileData);
                    logger.debug("File uploaded successfully: " + String.valueOf(width));

                } catch (Exception e) {
                    // If anay file upload failed  - > add to failed file list
                    failedFiles.add(file.getOriginalFilename());
                    logger.error("Failed to upload file: " + file.getOriginalFilename(), e); // Ghi lại log lỗi
                }
            }
        }

        // Handle delete file when any file had uploaded fail !
        if (!failedFiles.isEmpty()) {
            deleteUploadedFiles(uploadedFiles);
            Map<String, String> errors = new HashMap<>();
            errors.put("Upload failed for files:", String.join(", ", failedFiles));
            throw new CustomException(errors);
        }

        return uploadedFiles;
    }

    // Deleted list upload if error
    private void deleteUploadedFiles(List<Map<String, String>> uploadedFiles) {
        for (Map<String, String> fileData : uploadedFiles) {
            try {
                String publicId = fileData.get("fileId");
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
