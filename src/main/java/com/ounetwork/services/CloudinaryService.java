/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface CloudinaryService {
    String uploadFile(MultipartFile file) throws IOException;
}
