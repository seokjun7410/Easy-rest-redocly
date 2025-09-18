package com.example.demo.docs.user;

import com.example.demo.dto.error.ErrorResponse;
import com.example.demo.dto.error.ValidationErrorResponse;
import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * 사용자 생성 API 문서화
 *
 * 이 클래스는 기본적인 BaseDocs 사용법을 보여주는 예제입니다.
 * - Request/Response 클래스 지정
 * - 에러 응답 문서화
 * - 간단한 설정으로 완성되는 문서화
 */
public class UserCreateDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "user-create";
    }

    @Override
    public String getDescription() {
        return "새로운 사용자를 생성합니다. 필수 정보(사용자명, 이메일, 비밀번호, 실명)를 입력받아 새 계정을 만듭니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 생성 API";
    }

    @Override
    public Optional<Class<?>> getRequestClass() {
        return Optional.of(UserCreateRequest.class);
    }

    @Override
    public Optional<Class<?>> getResponseClass() {
        return Optional.of(UserResponse.class);
    }

    /**
     * 에러 응답 문서화
     * 다양한 HTTP 상태 코드별로 에러 응답을 정의할 수 있습니다.
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.BAD_REQUEST, ValidationErrorResponse.class,
                "입력 데이터 검증 실패 - 필수 필드 누락 또는 형식 오류")
            .addErrorResponse(HttpStatus.CONFLICT, ErrorResponse.class,
                "이미 존재하는 사용자명 또는 이메일")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}