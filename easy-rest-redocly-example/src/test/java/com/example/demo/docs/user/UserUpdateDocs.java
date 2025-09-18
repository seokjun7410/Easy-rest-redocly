package com.example.demo.docs.user;

import com.example.demo.dto.error.ErrorResponse;
import com.example.demo.dto.error.ValidationErrorResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;

import java.util.Optional;

/**
 * 사용자 수정 API 문서화
 *
 * 이 클래스는 복합적인 API 문서화 예제입니다:
 * - Path Parameter + Request Body + Response
 * - Query Parameter (추가 옵션)
 * - 다양한 에러 상황 문서화
 * - 모든 기능을 조합한 완성형 예제
 */
public class UserUpdateDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "user-update";
    }

    @Override
    public String getDescription() {
        return "사용자 정보를 업데이트합니다. 부분 업데이트를 지원하며, 제공된 필드만 수정됩니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 정보 수정 API";
    }

    @Override
    public Optional<Class<?>> getRequestClass() {
        return Optional.of(UserUpdateRequest.class);
    }

    @Override
    public Optional<Class<?>> getResponseClass() {
        return Optional.of(UserResponse.class);
    }

    /**
     * Path Parameter 문서화
     */
    public PathParametersSnippet getPathParameters() {
        return paramBuilder()
            .param("id", "수정할 사용자의 고유 ID")
            .buildPathParameters();
    }

    /**
     * Query Parameter 문서화
     * 추가적인 업데이트 옵션들을 제공
     */
    public QueryParametersSnippet getQueryParameters() {
        return paramBuilder()
            .param("validate", "데이터 검증 수행 여부 (기본값: true)", true)
            .param("notify", "변경 알림 발송 여부 (기본값: false)", true)
            .buildQueryParameters();
    }

    /**
     * 에러 응답 문서화
     * 수정 API에서 발생할 수 있는 다양한 에러 상황들
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.NOT_FOUND, ErrorResponse.class,
                "존재하지 않는 사용자 ID")
            .addErrorResponse(HttpStatus.BAD_REQUEST, ValidationErrorResponse.class,
                "잘못된 요청 데이터 - 필드 형식 오류")
            .addErrorResponse(HttpStatus.CONFLICT, ErrorResponse.class,
                "이미 사용 중인 이메일 주소")
            .addErrorResponse(HttpStatus.FORBIDDEN, ErrorResponse.class,
                "해당 사용자 정보 수정 권한 없음")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}