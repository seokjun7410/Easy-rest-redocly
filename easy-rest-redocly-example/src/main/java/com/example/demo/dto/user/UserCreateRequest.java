package com.example.demo.dto.user;

import com.example.easyrestredoclylib.core.DocsDescription;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {

    @DocsDescription("사용자명 (3-20자)")
    @NotBlank(message = "사용자명은 필수입니다")
    @Size(min = 3, max = 20, message = "사용자명은 3-20자 사이여야 합니다")
    private String username;

    @DocsDescription("사용자 이메일 주소")
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이어야 합니다")
    private String email;

    @DocsDescription("사용자 비밀번호 (8자 이상)")
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
    private String password;

    @DocsDescription("사용자 실명")
    @NotBlank(message = "이름은 필수입니다")
    private String fullName;

    @DocsDescription("사용자 나이 (선택사항)")
    private Integer age;

    @DocsDescription("사용자 휴대폰 번호 (선택사항)")
    private String phoneNumber;

    public UserCreateRequest() {}

    public UserCreateRequest(String username, String email, String password, String fullName, Integer age, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}