/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.utils;

import com.fasterxml.jackson.annotation.JsonView;
import com.ounetwork.views.View;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePackage {
    @JsonView(View.Detailed.class)
    private boolean success;
    
    
    @JsonView(View.Detailed.class)
    private Object data;
    
    
    @JsonView(View.Detailed.class)
    private String message;
    
    
    @JsonView(View.Summary.class)
    private Map<String, String> errors;
}
