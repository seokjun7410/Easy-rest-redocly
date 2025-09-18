package com.example.demo.docs.auth;

import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.dto.common.LoginType;
import com.example.demo.dto.error.ErrorResponse;
import com.example.demo.dto.error.ValidationErrorResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.request.FormParametersSnippet;

import java.util.Optional;

/**
 * Form 로그인 API 문서화
 *
 * 이 클래스는 Form Parameter 사용법을 보여주는 예제입니다:
 * - FormParamBuilder 사용법
 * - 필수/선택적 Form Parameter
 * - Enum 타입 Form Parameter
 * - application/x-www-form-urlencoded 처리
 */
public class LoginFormDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "auth-login-form";
    }

    @Override
    public String getDescription() {
        return "Form 데이터를 이용한 사용자 로그인. application/x-www-form-urlencoded 형식으로 인증 정보를 전송합니다.";
    }

    @Override
    public String getSummary() {
        return "Form 로그인 API";
    }

    @Override
    public Optional<Class<?>> getResponseClass() {
        return Optional.of(LoginResponse.class);
    }

    /**
     * Form Parameter 문서화
     * formParamBuilder()를 사용하여 Form 파라미터를 문서화
     */
    public FormParametersSnippet getFormParameters() {
        return formParamBuilder()
            .param("username", "사용자명 또는 이메일")
            .param("password", "비밀번호")
            .param("rememberMe", "로그인 상태 유지 (기본값: false)", true) // 선택적 파라미터
            .param("loginType", LoginType.class) // Enum 타입 - 자동으로 가능한 값들이 문서화됨
            .buildFormParameters();
    }

    /**
     * 에러 응답 문서화
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.BAD_REQUEST, ValidationErrorResponse.class,
                "필수 파라미터 누락 또는 형식 오류")
            .addErrorResponse(HttpStatus.UNAUTHORIZED, ErrorResponse.class,
                "잘못된 사용자명 또는 비밀번호")
            .addErrorResponse(HttpStatus.FORBIDDEN, ErrorResponse.class,
                "계정이 잠겨있거나 비활성화됨")
            .addErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ErrorResponse.class,
                "로그인 시도 횟수 초과")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}