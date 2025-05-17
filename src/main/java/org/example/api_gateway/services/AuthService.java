package org.example.api_gateway.services;

import org.example.api_gateway.models.User;
import org.example.api_gateway.models.dto.JwtResponse;
import org.example.api_gateway.models.dto.LoginRequest;
import org.example.api_gateway.models.enums.Role;
import org.example.api_gateway.repository.UserRepository;
import org.example.api_gateway.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse login(LoginRequest request) {
        logger.info("Login attempt for user: {}", request.getLogin());

        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        logger.info("User found: {}", user.getLogin());

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        logger.info("Password match result: {}", passwordMatches);

        if (!passwordMatches) {
            logger.warn("Invalid password attempt for user: {}", request.getLogin());
            throw new RuntimeException("Invalid password");
        }

        Role role = user.getRole();
        logger.info("User role: {}", role);

        String token = jwtUtils.generateToken(user.getLogin(), role);

        logger.info("JWT token generated for user: {}", user.getLogin());

        return new JwtResponse(token, user.getLogin(), role);
    }

}
