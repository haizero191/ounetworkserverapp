/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ounetwork.validation.annotation.Numberic;
import com.ounetwork.validation.annotation.UniqueField;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;

import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotBlank(message = "Email must be required")
    @Email(message = "Email isn't valid")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Student ID must be required")
    @Numberic(message = "The student ID must be numeric")
    @Size(min = 10, max = 10, message = "Student ID must have 10 characters as digits")
    @Column(name = "studentID")
    //@UniqueField(table = "User", field = "studentID", message = "Student ID number has been registered and waiting for verification")
    private String studentID;
    
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @NotBlank(message = "Password must be required")
    @Column(name = "password")
    private String password;

    @Column(name = "isApproved")
    private Boolean isApproved = false;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isApproved = false;
    }
}
