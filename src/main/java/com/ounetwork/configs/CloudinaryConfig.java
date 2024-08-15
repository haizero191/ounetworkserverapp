/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author Admin
 */
@Configuration
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryConfig {

    @Autowired
    private Environment env;
    
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.getProperty("cloudinary.connection.cloudName"),
                "api_key", env.getProperty("cloudinary.connection.apiKey"),
                "api_secret", env.getProperty("cloudinary.connection.apiSecret")
        ));
    }
}
