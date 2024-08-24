/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.views.View;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "media")
@Data
@Getter
@Setter
public class Media implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @JsonView(View.Summary.class)
    private String id;

    @Column(name = "url")
    @JsonView(View.Summary.class)
    private String url;

    @Column(name = "fileId")
    private String fileId;

    @Column(name = "fileType")
    private String fileType;

    @Column(name = "fileSize")
    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Post post;

    @Column(name = "uploadedAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime uploadedAt = LocalDateTime.now();


    @JsonView(View.Detailed.class)
    @Column(name = "width")
    private String width = "0";

    @JsonView(View.Detailed.class)
    @Column(name = "height")
    private String height = "0";
}
