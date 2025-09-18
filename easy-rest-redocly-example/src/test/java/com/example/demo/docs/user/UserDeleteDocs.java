package com.example.demo.docs.user;

import com.example.demo.dto.error.ErrorResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.request.PathParametersSnippet;

/**
 * 사용자 삭제 API 문서화
 *
 * 이 클래스는 Response가 없는 API 문서화 예제입니다:
 * - Path Parameter만 있는 API
 * - 204 No Content 응답
 * - Request/Response 클래스 없이 문서화
 */
public class UserDeleteDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "user-delete";
    }

    @Override
    public String getDescription() {
        return "사용자를 삭제합니다. 삭제된 사용자 정보는 복구할 수 없습니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 삭제 API";
    }

    // getRequestClass()와 getResponseClass()를 구현하지 않음
    // -> 요청/응답 본문이 없는 API임을 의미

    /**
     * Path Parameter 문서화
     */
    public PathParametersSnippet getPathParameters() {
        return paramBuilder()
            .param("id", "삭제할 사용자의 고유 ID")
            .buildPathParameters();
    }

    /**
     * 에러 응답 문서화
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.NOT_FOUND, ErrorResponse.class,
                "존재하지 않는 사용자 ID")
            .addErrorResponse(HttpStatus.FORBIDDEN, ErrorResponse.class,
                "해당 사용자 삭제 권한 없음")
            .addErrorResponse(HttpStatus.CONFLICT, ErrorResponse.class,
                "다른 데이터에서 참조 중인 사용자 (삭제 불가)")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}