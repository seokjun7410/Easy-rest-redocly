package com.example.demo.dto.auth;

import com.example.easyrestredoclylib.core.DocsDescription;
import java.time.LocalDateTime;

public class LoginResponse {

    @DocsDescription("JWT 액세스 토큰")
    private String accessToken;

    @DocsDescription("리프레시 토큰")
    private String refreshToken;

    @DocsDescription("토큰 타입 (Bearer)")
    private String tokenType = "Bearer";

    @DocsDescription("토큰 만료 시간 (초)")
    private Long expiresIn;

    @DocsDescription("토큰 발급 시간")
    private LocalDateTime issuedAt;

    @DocsDescription("사용자 정보")
    private UserInfo user;

    public LoginResponse() {}

    public LoginResponse(String accessToken, String refreshToken, Long expiresIn, UserInfo user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
        this.issuedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public static class UserInfo {
        @DocsDescription("사용자 ID")
        private Long id;

        @DocsDescription("사용자명")
        private String username;

        @DocsDescription("사용자 이메일")
        private String email;

        @DocsDescription("사용자 실명")
        private String fullName;

        public UserInfo() {}

        public UserInfo(Long id, String username, String email, String fullName) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}