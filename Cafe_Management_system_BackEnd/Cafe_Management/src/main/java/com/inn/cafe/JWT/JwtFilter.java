package com.inn.cafe.JWT;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                log.info("Inside doFilterInternal");

        // Skip filter for login, forgot password, and signup
        if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("request {}",request);

        String authorizationHeader = request.getHeader("Authorization");
        log.info("authorizationHeader {}",authorizationHeader);

        String token = null;
        String userName = null;
        Claims claims = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
                claims = jwtUtil.extratAllClaims(token); // Fixed method name typo
            } else {
                log.warn("Missing or invalid Authorization header");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
                return;
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(userName);

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // Enforce role-based access control
                    if (!isAdmin(claims) && request.getServletPath().startsWith("/admin")) {
                        log.warn("Unauthorized access attempt to admin route by user: {}", userName);
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error during JWT authentication: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        // Continue the request
        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    public boolean isAdmin(Claims claims) {
        if (claims == null) {
            return false;
        }

        Object roleObject = claims.get("role");

        if (roleObject instanceof String) {
            log.info("User role: {}", roleObject);
            return "admin".equalsIgnoreCase((String) roleObject);
        } else if (roleObject instanceof List) {
            List<String> roles = (List<String>) roleObject;
            log.info("User roles: {}", roles);
            return roles.contains("admin");
        }

        return false;
    }

    public boolean isUser(Claims claims) {
        if (claims == null) {
            return false;
        }

        Object roleObject = claims.get("role");

        if (roleObject instanceof String) {
            return "user".equalsIgnoreCase((String) roleObject);
        } else if (roleObject instanceof List) {
            List<String> roles = (List<String>) roleObject;
            return roles.contains("user");
        }

        return false;
    }

    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
