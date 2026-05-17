package com.crown.service;

import com.crown.dto.AuthRequest;
import com.crown.dto.AuthResponse;
import com.crown.entity.User;
import com.crown.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * Handles user registration and login.
 * For the demo, passwords are stored as plain text and JWT is simulated.
 * TODO: Integrate BCrypt + real JWT (io.jsonwebtoken) for production.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Register a new user.
     */
    public AuthResponse register(AuthRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // TODO: Hash password with BCrypt before saving
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // Plain text for demo
                .build();

        User saved = userRepository.save(user);

        return buildAuthResponse(saved);
    }

    /**
     * Login an existing user.
     */
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas."));

        // TODO: Compare with BCrypt hash for production
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

        return buildAuthResponse(user);
    }

    /**
     * Builds a simulated JWT auth response.
     */
    private AuthResponse buildAuthResponse(User user) {
        // Simulated JWT token for demo purposes
        String simulatedToken = Base64.getEncoder().encodeToString(
                (user.getId() + ":" + user.getEmail() + ":" + System.currentTimeMillis()).getBytes()
        );

        return AuthResponse.builder()
                .token("demo." + simulatedToken)
                .user(AuthResponse.UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .build())
                .build();
    }
}
