package com.cloudsyncx.service;

import com.cloudsyncx.dto.LoginRequest;
import com.cloudsyncx.dto.RegisterRequest;
import com.cloudsyncx.entity.User;
import com.cloudsyncx.repository.UserRepository;
import com.cloudsyncx.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .build();

        userRepository.save(user);

        return jwtUtil.generateToken(
                user.getEmail());
    }

    public String login(LoginRequest request) {

        User user =
                userRepository.findByEmail(
                                request.getEmail())
                        .orElseThrow();

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Credentials");
        }

        return jwtUtil.generateToken(
                user.getEmail());
    }
}