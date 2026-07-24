package com.demo.merchant.service.impl;

import com.demo.merchant.dto.auth.LoginRequest;
import com.demo.merchant.dto.auth.LoginResponse;
import com.demo.merchant.dto.auth.RegisterRequest;
import com.demo.merchant.entity.User;
import com.demo.merchant.repository.UserRepository;
import com.demo.merchant.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        throw new UnsupportedOperationException(
                "JWT implementation will be added in the next step.");
    }
}