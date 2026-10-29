package com.cloudsyncx.controller;

import com.cloudsyncx.dto.AuthResponse;
import com.cloudsyncx.dto.LoginRequest;
import com.cloudsyncx.dto.RegisterRequest;
import com.cloudsyncx.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest request) {

        String token =
                authService.register(request);

        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        String token =
                authService.login(request);

        return new AuthResponse(token);
    }
}