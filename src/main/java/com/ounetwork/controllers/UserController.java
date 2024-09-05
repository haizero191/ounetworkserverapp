/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.models.Media;
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
import com.ounetwork.services.CloudinaryService;
import com.ounetwork.services.MediaService;
import com.ounetwork.services.ProfileService;
import com.ounetwork.services.SpecService;
import com.ounetwork.views.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/private/users/profile")
    @Transactional
    public Profile index(HttpServletRequest request) {
        String studentId = (String) request.getAttribute("studentId");
        User user = this.userService.getUserByStudentID(studentId);
        return user.getProfile();
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
            @RequestParam(value = "avatar", required = false) MultipartFile[] avatar
    ) throws IOException {

        String studentId = (String) request.getAttribute("studentId");
        User user = this.userService.getUserByStudentID(studentId);
        Map<String, String> errors = new HashMap<>();

        if (avatar.length > 1) {
            errors.put("avatar", "Your avatar only one image");
        }

        // Handle profile updated
        Profile profileData = user.getProfile();
        if (profileData != null) {
            profileData.setFirstName(firstName);
            profileData.setLastName(lastName);
            profileData.setIntroduce(introduce);
            profileData.setPhone(phone);
            profileData.setAddress(address);
            profileData.setBirthdate(birthdate);

            // Kiểm tra Spec
            if (spec != null) {
                Spec specUpdate = this.specService.getSpecById(spec);
                if (specUpdate == null) {
                    errors.put("spec", "can't find this data");
                } else {
                    profileData.setSpec(specUpdate);
                }
            }

            
            
            // Kiểm tra avatar
            if (profileData.getAvatar() == null && avatar.length != 0) {
                // Upload image to cloudinary
                List<Map<String, String>> uploadedFilesResult = new ArrayList<>();
                uploadedFilesResult = this.cloudinaryService.uploadMutilFiles(avatar);
                Map<String, String> avatarUploaded = uploadedFilesResult.get(0);

                Media newMedia = new Media();
                newMedia.setFileId(avatarUploaded.get("fileId"));
                newMedia.setFileType(avatarUploaded.get("fileType"));
                newMedia.setUrl(avatarUploaded.get("fileUrl"));

                // Get width and height of media
                newMedia.setWidth(avatarUploaded.get("width"));
                newMedia.setHeight(avatarUploaded.get("height"));
                try {
                    // Save media into database
                    this.mediaService.create(newMedia);
                    profileData.setAvatar(newMedia);
                } catch (Exception e) {
                    errors.put("media", "Failed to create media");
                }
            }
            if (profileData.getAvatar() != null && avatar.length != 0) {
                Media avatarExist = profileData.getAvatar();

                // Upload image to cloudinary
                List<Map<String, String>> uploadedFilesResult = new ArrayList<>();
                uploadedFilesResult = this.cloudinaryService.uploadMutilFiles(avatar);
                Map<String, String> avatarUploaded = uploadedFilesResult.get(0);
                // Deleted old avatar here ... 

                // Set updated data for exist media
                avatarExist.setFileId(avatarUploaded.get("fileId"));
                avatarExist.setFileType(avatarUploaded.get("fileType"));
                avatarExist.setUrl(avatarUploaded.get("fileUrl"));

                // Get width and height of media
                avatarExist.setWidth(avatarUploaded.get("width"));
                avatarExist.setHeight(avatarUploaded.get("height"));
                profileData.setAvatar(avatarExist);
            }
        }

        if (!errors.isEmpty() && errors != null) {
            return new ResponseEntity(new ResponsePackage(false, null, "Your profile update failed", errors), HttpStatus.BAD_REQUEST);
        }

        try {
            Profile updatedProfile = this.profileService.updated(profileData);
            return new ResponseEntity(new ResponsePackage(true, profileData, "Your profile updated", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponsePackage(false, null, "Your profile updated fail !" + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
