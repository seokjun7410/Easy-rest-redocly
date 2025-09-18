package com.example.demo.dto.auth;

import com.example.easyrestredoclylib.core.DocsDescription;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @DocsDescription("사용자명 또는 이메일")
    @NotBlank(message = "사용자명은 필수입니다")
    private String username;

    @DocsDescription("비밀번호")
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    @DocsDescription("로그인 상태 유지 (선택사항)")
    private Boolean rememberMe;

    public LoginRequest() {}

    public LoginRequest(String username, String password, Boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}