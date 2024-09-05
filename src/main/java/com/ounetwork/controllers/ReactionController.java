/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.services.ReactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ounetwork.models.Reaction;
import com.ounetwork.utils.JwtUtil;
import com.ounetwork.views.View;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/v1/private/reactions")
public class ReactionController {
    @Autowired
    private ReactionService reactionService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getall/{postId}")
    @CrossOrigin
    @JsonView(View.Summary.class)
    public List<Reaction> getReactFromPost(@PathVariable String postId) {
        return this.reactionService.getReactionFromPost(postId);
    }
}
