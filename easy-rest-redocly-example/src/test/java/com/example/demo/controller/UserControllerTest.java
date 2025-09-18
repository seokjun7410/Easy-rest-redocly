package com.example.demo.controller;

import com.example.demo.docs.user.*;
import com.example.demo.dto.common.UserStatus;
import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

/**
 * UserController REST Docs 테스트
 *
 * 이 클래스는 easy-rest-redocly 라이브러리의 기본적인 사용법을 보여주는 예제입니다.
 * 각 테스트 메서드는 다른 종류의 API 문서화 케이스를 다룹니다.
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestSpecification documentationSpec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.documentationSpec = given()
            .filter(documentationConfiguration(restDocumentation));
    }

    /**
     * 사용자 생성 API 테스트
     * - Request/Response Body 문서화
     * - 기본적인 BaseDocs 사용법
     */
    @Test
    void testCreateUser() throws Exception {
        UserCreateDocs docs = new UserCreateDocs();

        UserCreateRequest request = new UserCreateRequest(
            "testuser",
            "test@example.com",
            "password123",
            "Test User",
            25,
            "010-1234-5678"
        );

        given(this.documentationSpec)
            .filter(docs.successFilter())
            .contentType("application/json")
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post("/api/users")
        .then()
            .statusCode(201);
    }

    /**
     * 사용자 조회 API 테스트
     * - Path Parameter 문서화
     * - Response만 있는 API 문서화
     */
    @Test
    void testGetUser() throws Exception {
        UserGetDocs docs = new UserGetDocs();

        given(this.documentationSpec)
            .filter(docs.successFilter())
            .filter(document("user-get", docs.getPathParameters()))
            .pathParam("id", 1L)
        .when()
            .get("/api/users/{id}")
        .then()
            .statusCode(200);
    }

    /**
     * 사용자 목록 조회 API 테스트
     * - Query Parameter 문서화 (필수/선택적)
     * - Enum 타입 파라미터
     * - List Response 문서화
     */
    @Test
    void testGetUsers() throws Exception {
        UserListDocs docs = new UserListDocs();

        given(this.documentationSpec)
            .filter(docs.successFilter())
            .filter(document("user-list", docs.getQueryParameters()))
            .queryParam("name", "test")
            .queryParam("status", UserStatus.ACTIVE)
            .queryParam("page", 0)
            .queryParam("size", 10)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200);
    }

    /**
     * 사용자 수정 API 테스트
     * - Path Parameter + Request Body + Response
     * - Query Parameter (추가 옵션)
     * - 복합적인 API 문서화
     */
    @Test
    void testUpdateUser() throws Exception {
        UserUpdateDocs docs = new UserUpdateDocs();

        UserUpdateRequest request = new UserUpdateRequest(
            "newemail@example.com",
            "Updated User",
            30,
            "010-9999-9999",
            UserStatus.ACTIVE
        );

        given(this.documentationSpec)
            .filter(docs.successFilter())
            .filter(document("user-update",
                docs.getPathParameters(),
                docs.getQueryParameters()))
            .contentType("application/json")
            .pathParam("id", 1L)
            .queryParam("validate", "true")
            .queryParam("notify", "false")
            .body(objectMapper.writeValueAsString(request))
        .when()
            .put("/api/users/{id}")
        .then()
            .statusCode(200);
    }

    /**
     * 사용자 삭제 API 테스트
     * - Path Parameter만 있는 API
     * - Request/Response Body 없는 API
     */
    @Test
    void testDeleteUser() throws Exception {
        UserDeleteDocs docs = new UserDeleteDocs();

        given(this.documentationSpec)
            .filter(docs.successFilter())
            .filter(document("user-delete", docs.getPathParameters()))
            .pathParam("id", 1L)
        .when()
            .delete("/api/users/{id}")
        .then()
            .statusCode(204);
    }

    /**
     * 에러 케이스 테스트 예제 - 사용자 생성 검증 실패
     */
    @Test
    void testCreateUserValidationError() throws Exception {
        UserCreateDocs docs = new UserCreateDocs();

        // 잘못된 요청 데이터 (필수 필드 누락)
        UserCreateRequest invalidRequest = new UserCreateRequest();
        invalidRequest.setUsername("ab"); // 너무 짧은 사용자명
        invalidRequest.setEmail("invalid-email"); // 잘못된 이메일 형식

        given(this.documentationSpec)
            .filter(docs.validationFilterInRestAssured("user-create-validation-error"))
            .contentType("application/json")
            .body(objectMapper.writeValueAsString(invalidRequest))
        .when()
            .post("/api/users")
        .then()
            .statusCode(400);
    }

    /**
     * 에러 케이스 테스트 예제 - 사용자 조회 Not Found
     */
    @Test
    void testGetUserNotFound() throws Exception {
        UserGetDocs docs = new UserGetDocs();

        given(this.documentationSpec)
            .filter(document("user-get-not-found", docs.getPathParameters()))
            .pathParam("id", 999L) // 존재하지 않는 ID
        .when()
            .get("/api/users/{id}")
        .then()
            .statusCode(404);
    }
}