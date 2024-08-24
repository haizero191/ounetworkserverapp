/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ounetwork.repositories;
import com.ounetwork.models.Media;
import java.util.List;
/**
 *
 * @author Admin
 */
public interface MediaRepository {
    List<Media> getAll();
    Media creare(Media media);
}
