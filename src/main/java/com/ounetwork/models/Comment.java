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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "comments")
@Data
@Getter
@Setter
public class Comment implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @JsonView(View.Summary.class)
    private String id;

    @Column(name = "postId")
    @JsonView(View.Detailed.class)
    private String postId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonView(View.Summary.class)
    private User user;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    @JsonView(View.Detailed.class)
    private String content;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonView(View.Detailed.class)
    private LocalDateTime createdAt = LocalDateTime.now();

}
