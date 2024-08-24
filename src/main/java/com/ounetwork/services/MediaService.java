/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.services;
import com.ounetwork.models.Media;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author Admin
 */
public interface MediaService {
    List<Media> getAll();
    Media create(Media media);
}
