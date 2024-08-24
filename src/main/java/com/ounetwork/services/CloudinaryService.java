/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface CloudinaryService {

    List<Map<String, String>> uploadMutilFiles(MultipartFile[] files) throws IOException;
//    Map<String, Object> deletedFile(String fileId) throws IOException;
}
