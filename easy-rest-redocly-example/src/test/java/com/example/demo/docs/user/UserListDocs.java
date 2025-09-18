package com.example.demo.docs.user;

import com.example.demo.dto.common.UserStatus;
import com.example.demo.dto.error.ErrorResponse;
import com.example.demo.dto.user.UserResponse;
import com.example.easyrestredoclylib.core.BaseDocs;
import com.example.easyrestredoclylib.core.ErrorResponseDocs;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.request.QueryParametersSnippet;

import java.util.Optional;

/**
 * 사용자 목록 조회 API 문서화
 *
 * 이 클래스는 다음 기능들을 보여주는 예제입니다:
 * - List Response 문서화 (getListResponseClass 사용)
 * - Query Parameter 문서화 (필수/선택적 파라미터)
 * - Enum 타입 파라미터 문서화
 * - 페이징 파라미터 문서화
 */
public class UserListDocs extends BaseDocs {

    @Override
    public String getIdentifier() {
        return "user-list";
    }

    @Override
    public String getDescription() {
        return "사용자 목록을 조회합니다. 이름 검색, 상태 필터링, 페이징을 지원합니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 목록 조회 API";
    }

    /**
     * List Response 사용 예제
     * getListResponseClass()를 사용하면 Page<UserResponse> 형태로 문서화됩니다.
     */
    @Override
    public Optional<Class<?>> getListResponseClass() {
        return Optional.of(UserResponse.class);
    }

    /**
     * Query Parameter 문서화
     * - 필수 파라미터와 선택적 파라미터 구분
     * - Enum 타입 파라미터 자동 문서화
     * - 기본값이 있는 파라미터 처리
     */
    public QueryParametersSnippet getQueryParameters() {
        return paramBuilder()
            .param("name", "검색할 사용자명 (부분 일치)", true) // 선택적 파라미터
            .param("status", UserStatus.class) // Enum 타입 - 자동으로 가능한 값들이 문서화됨
            .param("page", "페이지 번호 (0부터 시작, 기본값: 0)", true)
            .param("size", "페이지 크기 (기본값: 10)", true)
            .buildQueryParameters();
    }

    /**
     * 에러 응답 문서화
     */
    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.BAD_REQUEST, ErrorResponse.class,
                "잘못된 쿼리 파라미터 (음수 페이지, 잘못된 상태값 등)")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}