package com.example.demo.dto.error;

import com.example.easyrestredoclylib.core.DocsDescription;
import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    @DocsDescription("에러 코드")
    private String code;

    @DocsDescription("에러 메시지")
    private String message;

    @DocsDescription("상세 에러 메시지")
    private String details;

    @DocsDescription("에러 발생 시간")
    private LocalDateTime timestamp;

    @DocsDescription("요청 경로")
    private String path;

    @DocsDescription("HTTP 상태 코드")
    private Integer status;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message, String details, String path, Integer status) {
        this();
        this.code = code;
        this.message = message;
        this.details = details;
        this.path = path;
        this.status = status;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}