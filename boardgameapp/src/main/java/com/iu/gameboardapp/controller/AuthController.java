package com.iu.gameboardapp.controller;

import com.iu.gameboardapp.service.JwtService;
import com.iu.gameboardapp.service.UserService;
import dtos.LoginRequest;
import dtos.LoginResponse;
import dtos.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(authentication.getName());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.registerNewUser(registerRequest);
        return ResponseEntity.ok("User registered successfully!");
    }
}
