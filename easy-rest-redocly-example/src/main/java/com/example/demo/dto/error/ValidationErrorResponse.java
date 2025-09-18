package com.example.demo.dto.error;

import com.example.easyrestredoclylib.core.DocsDescription;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {

    @DocsDescription("필드별 검증 에러 목록")
    private List<FieldError> fieldErrors;

    public ValidationErrorResponse() {
        super();
    }

    public ValidationErrorResponse(String message, String path, List<FieldError> fieldErrors) {
        super("VALIDATION_ERROR", message, "입력 데이터 검증에 실패했습니다", path, 400);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public static class FieldError {
        @DocsDescription("필드명")
        private String field;

        @DocsDescription("거부된 값")
        private Object rejectedValue;

        @DocsDescription("에러 메시지")
        private String message;

        public FieldError() {}

        public FieldError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        // Getters and Setters
        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}