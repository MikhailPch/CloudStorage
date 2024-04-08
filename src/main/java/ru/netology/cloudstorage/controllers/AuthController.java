package ru.netology.cloudstorage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudstorage.dto.AuthRequest;
import ru.netology.cloudstorage.dto.AuthResponse;
import ru.netology.cloudstorage.service.AuthorizationService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/")
public class AuthController {
    private final AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return authorizationService.loginUser(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader("auth-token") @NotBlank String authToken) {
        authorizationService.logoutUser(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}