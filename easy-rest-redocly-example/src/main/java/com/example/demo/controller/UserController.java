package com.example.demo.controller;

import com.example.demo.dto.common.UserStatus;
import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        // Mock implementation
        UserResponse response = new UserResponse(
            1L,
            request.getUsername(),
            request.getEmail(),
            request.getFullName(),
            request.getAge(),
            request.getPhoneNumber(),
            UserStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        // Mock implementation - return 404 for id 999
        if (id == 999L) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
            id,
            "testuser",
            "test@example.com",
            "Test User",
            25,
            "010-1234-5678",
            UserStatus.ACTIVE,
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        // Mock implementation
        UserResponse response = new UserResponse(
            id,
            "testuser",
            Optional.ofNullable(request.getEmail()).orElse("test@example.com"),
            Optional.ofNullable(request.getFullName()).orElse("Test User"),
            Optional.ofNullable(request.getAge()).orElse(25),
            Optional.ofNullable(request.getPhoneNumber()).orElse("010-1234-5678"),
            Optional.ofNullable(request.getStatus()).orElse(UserStatus.ACTIVE),
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Mock implementation
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Mock implementation
        List<UserResponse> users = Arrays.asList(
            new UserResponse(1L, "user1", "user1@example.com", "User One", 25, "010-1111-1111", UserStatus.ACTIVE, LocalDateTime.now().minusDays(30), LocalDateTime.now()),
            new UserResponse(2L, "user2", "user2@example.com", "User Two", 30, "010-2222-2222", UserStatus.ACTIVE, LocalDateTime.now().minusDays(20), LocalDateTime.now()),
            new UserResponse(3L, "user3", "user3@example.com", "User Three", 35, "010-3333-3333", UserStatus.INACTIVE, LocalDateTime.now().minusDays(10), LocalDateTime.now())
        );

        return ResponseEntity.ok(users);
    }
}