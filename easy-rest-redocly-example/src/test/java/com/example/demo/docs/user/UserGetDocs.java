package com.example.demo.docs.user;

import com.example.demo.dto.error.ErrorResponse;
import com.example.demo.dto.user.UserResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.request.PathParametersSnippet;

import java.util.Optional;

/**
 * 사용자 조회 API 문서화
 *
 * 이 클래스는 Path Parameter를 포함한 API 문서화 예제입니다.
 * - Path Parameter 문서화
 * - Response만 있는 API 문서화
 * - 에러 응답 (404 Not Found) 처리
 */
public class UserGetDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "user-get";
    }

    @Override
    public String getDescription() {
        return "사용자 ID를 이용하여 특정 사용자의 상세 정보를 조회합니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 조회 API";
    }

    @Override
    public Optional<Class<?>> getResponseClass() {
        return Optional.of(UserResponse.class);
    }

    /**
     * Path Parameter 문서화
     * paramBuilder()를 사용하여 경로 변수를 문서화할 수 있습니다.
     */
    public PathParametersSnippet getPathParameters() {
        return paramBuilder()
            .param("id", "조회할 사용자의 고유 ID")
            .buildPathParameters();
    }

    /**
     * 에러 응답 문서화
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.NOT_FOUND, ErrorResponse.class,
                "존재하지 않는 사용자 ID")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}