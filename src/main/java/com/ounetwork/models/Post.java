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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "posts")
@Data
@Getter
@Setter
public class Post implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    @JsonView(View.Detailed.class)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    @JsonView(View.Detailed.class)
    private User owner;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Detailed.class)
    private List<Media> mediaList;

    @NotBlank(message = "Content isn't blank")
    @Column(name = "content")
    @JsonView(View.Summary.class)
    private String content;

    @Column(name = "createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonView(View.Detailed.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonView(View.Detailed.class)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    
    
    // Field Additional
    @Transient // Tạo một trường không bền 
    @JsonView(View.Detailed.class)
    private Reaction userInteraction;
    
    // Field Additional
    @Transient // Tạo một trường không bền 
    @JsonView(View.Detailed.class)
    private long reactionNumber = 0;
    
    // Field Additional
    @Transient // Tạo một trường không bền 
    @JsonView(View.Detailed.class)
    private long commentNumber = 0;
    
    // Field Additional
    @Transient // Tạo một trường không bền 
    @JsonView(View.Detailed.class)
    private String reactionType = "DEFAULT";

    @Transient // Tạo một trường không bền 
    @JsonView(View.Detailed.class)
    private Boolean isUserInteracted = false;

}
