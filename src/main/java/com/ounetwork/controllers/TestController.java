/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    // Chỉ ROLE_TEACHER và ROLE_ADMIN mới có thể truy cập API này
    @GetMapping("/teacher-admin-endpoint")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public ResponseEntity<String> teacherAdminEndpoint() {
        return ResponseEntity.ok("Access granted to TEACHER or ADMIN");
    }

    // Chỉ ROLE_USER mới có thể truy cập API này
    @GetMapping("/user-endpoint")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("Access granted to USER");
    }

    // Chỉ ROLE_ADMIN mới có thể truy cập API này
    @GetMapping("/admin-endpoint")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Access granted to ADMIN");
    }
}
