package com.example.demo.dto.user;

import com.example.demo.dto.common.UserStatus;
import com.example.easyrestredoclylib.core.DocsDescription;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

    @DocsDescription("사용자 이메일 주소 (선택사항)")
    @Email(message = "올바른 이메일 형식이어야 합니다")
    private String email;

    @DocsDescription("사용자 실명 (선택사항)")
    private String fullName;

    @DocsDescription("사용자 나이 (선택사항)")
    private Integer age;

    @DocsDescription("사용자 휴대폰 번호 (선택사항)")
    private String phoneNumber;

    @DocsDescription("사용자 상태 (선택사항)")
    private UserStatus status;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String email, String fullName, Integer age, String phoneNumber, UserStatus status) {
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    // Getters and Setters
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
}