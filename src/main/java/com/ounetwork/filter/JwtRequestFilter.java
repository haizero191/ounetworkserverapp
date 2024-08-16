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
        logger.debug("auth header: " + authorizationHeader);
        String username = null;
        String role = null;
        String jwtToken = null;

        // Extract token and username from header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);

            // Check Valid JWT from request
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    role = jwtUtil.extractRole(jwtToken);

                    // Create new UserDetails
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    logger.debug("\u001B[33m" + "User Role From JWT request: " + userDetails.getAuthorities() + "\u001B[0m");

                    if (jwtUtil.validateToken(jwtToken, username)) {
                        // Lấy giá trị isApproved từ JWT
                        boolean isApproved = jwtUtil.extractIsApproved(jwtToken);

                        if (isApproved) {
                            // Create an authentication token and set it in the SecurityContext
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        } else {
                            // Người dùng không được phê duyệt, từ chối truy cập
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not approved. please wait to access by admin");
                            return;
                        }

                    }
                } catch (UsernameNotFoundException e) {
                    // Handle user not found
                }
            }
        }

        // Go to next filter chain
        chain.doFilter(request, response);
    }

}
