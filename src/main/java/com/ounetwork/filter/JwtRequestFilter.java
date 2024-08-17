/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.filter;

import com.ounetwork.utils.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
                        // Get isApproved from JWT
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
                            // Denied access Apis
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "User hasn't been approved. Please wait for admin confirmation your account");
                            return;
                        }
                    }
                } catch (UsernameNotFoundException e) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account does not exist, please login or register a new account");
                    return;
                }
            }
        }

        // Go to next filter chain
        chain.doFilter(request, response);
    }

}
