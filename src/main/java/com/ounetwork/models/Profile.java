/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.views.View;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "profiles")
@Data
@Getter
@Setter
public class Profile implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "firstName")
    @JsonView(View.Summary.class)
    private String firstName;

    @Column(name = "lastName")
    @JsonView(View.Summary.class)
    private String lastName;

    @Column(name = "introduce")
    private String introduce;

    @OneToOne
    @JoinColumn(name = "avatarId")
    @JsonView(View.Summary.class)
    private Media avatar;
    
    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "specId")
    @JsonView(View.Summary.class)
    private Spec spec;

    @Column(name = "address")
    private String address;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
