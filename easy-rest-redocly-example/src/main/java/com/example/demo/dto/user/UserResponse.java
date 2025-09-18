package com.example.demo.dto.user;

import com.example.demo.dto.common.UserStatus;
import com.example.easyrestredoclylib.core.DocsDescription;
import java.time.LocalDateTime;

public class UserResponse {

    @DocsDescription("사용자 고유 ID")
    private Long id;

    @DocsDescription("사용자명")
    private String username;

    @DocsDescription("사용자 이메일 주소")
    private String email;

    @DocsDescription("사용자 실명")
    private String fullName;

    @DocsDescription("사용자 나이")
    private Integer age;

    @DocsDescription("사용자 휴대폰 번호")
    private String phoneNumber;

    @DocsDescription("사용자 상태")
    private UserStatus status;

    @DocsDescription("계정 생성일시")
    private LocalDateTime createdAt;

    @DocsDescription("마지막 수정일시")
    private LocalDateTime updatedAt;

    public UserResponse() {}

    public UserResponse(Long id, String username, String email, String fullName, Integer age,
                       String phoneNumber, UserStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}