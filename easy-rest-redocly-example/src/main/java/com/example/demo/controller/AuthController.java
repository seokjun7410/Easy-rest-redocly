package com.example.demo.controller;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.dto.common.LoginType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * 인증 관련 API 컨트롤러
 *
 * Form Parameter를 사용하는 로그인 API 예제를 포함합니다.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Form 로그인 API
     * application/x-www-form-urlencoded로 데이터를 받는 예제
     */
    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<LoginResponse> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "false") Boolean rememberMe,
            @RequestParam(required = false, defaultValue = "NORMAL") LoginType loginType) {

        // Mock implementation
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            1L, username, username + "@example.com", "Test User"
        );

        LoginResponse response = new LoginResponse(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            "refresh_token_example",
            3600L, // 1 hour
            userInfo
        );

        return ResponseEntity.ok(response);
    }

    /**
     * JSON 로그인 API
     * application/json으로 데이터를 받는 예제
     */
    @PostMapping(value = "/login/json", consumes = "application/json")
    public ResponseEntity<LoginResponse> loginJson(@Valid @RequestBody LoginRequest request) {

        // Mock implementation
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            1L, request.getUsername(), request.getUsername() + "@example.com", "Test User"
        );

        LoginResponse response = new LoginResponse(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
            "refresh_token_example",
            3600L, // 1 hour
            userInfo
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 토큰 갱신 API
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam String refreshToken) {

        // Mock implementation
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            1L, "testuser", "testuser@example.com", "Test User"
        );

        LoginResponse response = new LoginResponse(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9_new...",
            "refresh_token_new_example",
            3600L, // 1 hour
            userInfo
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // Mock implementation
        return ResponseEntity.noContent().build();
    }
}