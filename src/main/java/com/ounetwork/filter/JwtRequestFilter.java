/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ounetwork.utils.JwtUtil;
import com.ounetwork.utils.ResponsePackage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Admin
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // Handling Authoritative Apis
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Extract token and username from header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);

            // Check Valid JWT from request
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    // Create new UserDetails
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(jwtToken, username)) {
                        Boolean isProtected = request.getRequestURI().contains("protected");
                        // Kiểm tra user đã đăng nhập chưa - Sử dụng để update profile
                        if (isProtected) {
                            // Chuyển JWT cho controller xử lí
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            // Chuyển JWT cho controller xử lí
                            request.setAttribute("jwtToken", jwtToken);
                            request.setAttribute("studentId", username);
                            // Go to next filter chain
                            chain.doFilter(request, response);
                        }

                        // Lấy isApproved từ JWT và kiểm tra
                        boolean isApproved = jwtUtil.extractIsApproved(jwtToken);
                        if (isApproved) {
                            // Create an authentication token and set it in the SecurityContext
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        } else {
                            sendErrorResponse(
                                    response,
                                    HttpServletResponse.SC_FORBIDDEN,
                                    "User hasn't been approved. Please wait for admin confirmation of your account"
                            );
                            return;
                        }
                    }
                } catch (UsernameNotFoundException e) {
                    sendErrorResponse(
                            response,
                            HttpServletResponse.SC_FORBIDDEN,
                            "Account does not exist, please login or register a new account"
                    );

                    return;
                }
            }
        }

        // Chuyển JWT cho controller xử lí
        request.setAttribute("jwtToken", jwtToken);
        // Go to next filter chain
        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorList = new HashMap<>();
        errorList.put("", status);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("success", false);
        errorResponse.put("data", null);

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }

}
