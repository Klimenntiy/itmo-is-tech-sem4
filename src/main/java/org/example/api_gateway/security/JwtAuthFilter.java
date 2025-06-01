package org.example.api_gateway.security;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.api_gateway.models.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("Authorization header: {}", request.getHeader("Authorization"));

        if ("/api/auth/login".equals(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = parseJwt(request);
            if (jwt != null) {
                logger.debug("JWT token found: {}", jwt);
                if (jwtUtils.validateToken(jwt)) {
                    String login = jwtUtils.getLoginFromToken(jwt);
                    Role role = jwtUtils.getRoleFromToken(jwt);
                    logger.debug("JWT token valid. User: {}, Role: {}", login, role);
                    var auth = new JwtAuthentication(login, role);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.warn("Invalid JWT token");
                }
            } else {
                logger.warn("No JWT token found in request header");
            }
        } catch (Exception e) {
            logger.error("Error validating JWT token", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
