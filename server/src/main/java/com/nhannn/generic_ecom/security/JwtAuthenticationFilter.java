package com.nhannn.generic_ecom.security;

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

/**
 * Author: nhannn
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUserDetailsService userService;
    private final JwtToken jwtToken;

    @Autowired
    public JwtAuthenticationFilter(JwtToken jwtToken, JwtUserDetailsService userService) {
        this.jwtToken = jwtToken;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestToken = request.getHeader(JwtToken.HEADER);
            String username = null;
            String token = null;
            if (requestToken != null && requestToken.startsWith(JwtToken.PREFIX)) {
                // Get jwtToken part
                token = requestToken.substring(JwtToken.PREFIX.length() + 1);

                // Extract username from the token
                username = jwtToken.getUsernameFromToken(token);
            }

            // Get user information and setAuthentication for SecurityContextHolder
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // load UserDetails
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtToken.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication context for SecurityContextHolder
                    SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception ex) {
            // ignore and continue
        }
        filterChain.doFilter(request, response);
    }
}
