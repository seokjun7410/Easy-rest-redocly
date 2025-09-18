# Easy-Rest-Redocly Example Module

이 모듈은 Easy-Rest-Redocly 라이브러리의 다양한 사용법을 보여주는 Recipe 스타일의 예제 모음입니다.

## 📚 목차

1. [기본 설정](#기본-설정)
2. [Recipe 예제들](#recipe-예제들)
   - [Recipe 1: 기본 CRUD API](#recipe-1-기본-crud-api)
   - [Recipe 2: Form Parameter API](#recipe-2-form-parameter-api)
   - [Recipe 3: 복합 Query Parameter](#recipe-3-복합-query-parameter)
   - [Recipe 4: 에러 응답 문서화](#recipe-4-에러-응답-문서화)
   - [Recipe 5: List Response](#recipe-5-list-response)
3. [실행 방법](#실행-방법)
4. [문서 생성](#문서-생성)

## 🚀 기본 설정

### 1. 의존성 추가

```kotlin
dependencies {
    implementation(project(":easy-rest-redocly-lib"))

    // Spring Boot & Validation
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // REST Docs 테스트
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    testImplementation("com.epages:restdocs-api-spec-restassured:0.18.2")
}
```

### 2. 테스트 설정

```java
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YourControllerTest {

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.documentationSpec = given()
            .filter(documentationConfiguration(restDocumentation));
    }
}
```

## 📖 Recipe 예제들

### Recipe 1: 기본 CRUD API

가장 기본적인 사용법을 보여주는 예제입니다.

#### 📁 파일 구조
```
src/main/java/com/example/demo/
├── controller/UserController.java
├── dto/user/
│   ├── UserCreateRequest.java
│   ├── UserResponse.java
│   └── UserUpdateRequest.java
└── docs/user/
    ├── UserCreateDocs.java
    ├── UserGetDocs.java
    └── UserUpdateDocs.java
```

#### 🔧 BaseDocs 구현

**1단계: 기본 정보 정의**

```java
public class UserCreateDocs extends BaseDocs {
    @Override
    public String getIdentifier() {
        return "user-create";
    }

    @Override
    public String getDescription() {
        return "새로운 사용자를 생성합니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 생성 API";
    }
}
```

**2단계: Request/Response 클래스 지정**

```java
@Override
public Optional<Class<?>> getRequestClass() {
    return Optional.of(UserCreateRequest.class);
}

@Override
public Optional<Class<?>> getResponseClass() {
    return Optional.of(UserResponse.class);
}
```

**3단계: 테스트에서 사용**

```java
@Test
void testCreateUser() throws Exception {
    UserCreateDocs docs = new UserCreateDocs();

    given(this.documentationSpec)
        .filter(docs.successFilter())  // 🎯 이것만으로 문서화 완료!
        .contentType("application/json")
        .body(request)
    .when()
        .post("/api/users")
    .then()
        .statusCode(201);
}
```

#### 💡 핵심 포인트
- `successFilter()` 하나로 Request/Response가 자동 문서화
- DTO 클래스의 `@DocsDescription`이 자동으로 필드 설명 생성
- Jakarta Validation 어노테이션이 자동으로 제약조건 문서화

---

### Recipe 2: Form Parameter API

Form 데이터를 사용하는 API 문서화 방법입니다.

#### 🔧 구현 방법

**1단계: FormDocs 클래스 생성**

```java
public class LoginFormDocs extends BaseDocs {
    @Override
    public String getIdentifier() {
        return "auth-login-form";
    }

    @Override
    public String getDescription() {
        return "Form 데이터를 이용한 사용자 로그인";
    }

    @Override
    public String getSummary() {
        return "Form 로그인 API";
    }

    @Override
    public Optional<Class<?>> getResponseClass() {
        return Optional.of(LoginResponse.class);
    }

    // 🎯 Form Parameter 문서화
    public FormParametersSnippet getFormParameters() {
        return formParamBuilder()
            .param("username", "사용자명 또는 이메일")
            .param("password", "비밀번호")
            .param("rememberMe", "로그인 상태 유지", true) // 선택적 파라미터
            .param("loginType", LoginType.class) // Enum 자동 문서화
            .buildFormParameters();
    }
}
```

**2단계: Controller 구현**

```java
@PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
public ResponseEntity<LoginResponse> login(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam(required = false) Boolean rememberMe,
        @RequestParam(required = false) LoginType loginType) {
    // 구현...
}
```

**3단계: 테스트 작성**

```java
@Test
void testFormLogin() throws Exception {
    LoginFormDocs docs = new LoginFormDocs();

    given(this.documentationSpec)
        .filter(docs.successFilter())
        .filter(document("auth-login-form", docs.getFormParameters())) // 🎯 Form 파라미터 문서화
        .contentType("application/x-www-form-urlencoded")
        .formParam("username", "testuser")
        .formParam("password", "password123")
        .formParam("rememberMe", "true")
        .formParam("loginType", "NORMAL")
    .when()
        .post("/api/auth/login")
    .then()
        .statusCode(200);
}
```

#### 💡 핵심 포인트
- `formParamBuilder()`로 Form Parameter 문서화
- Enum 타입 자동 문서화 지원
- 선택적 파라미터 구분 가능

---

### Recipe 3: 복합 Query Parameter

Query Parameter와 Path Parameter를 함께 사용하는 복합적인 API 예제입니다.

#### 🔧 구현 방법

```java
public class UserUpdateDocs extends BaseDocs {
    // 기본 설정...

    // Path Parameter 문서화
    public PathParametersSnippet getPathParameters() {
        return paramBuilder()
            .param("id", "수정할 사용자의 고유 ID")
            .buildPathParameters();
    }

    // Query Parameter 문서화
    public QueryParametersSnippet getQueryParameters() {
        return paramBuilder()
            .param("validate", "데이터 검증 수행 여부", true)
            .param("notify", "변경 알림 발송 여부", true)
            .buildQueryParameters();
    }
}
```

**테스트에서 사용:**

```java
given(this.documentationSpec)
    .filter(docs.successFilter())
    .filter(document("user-update",
        docs.getPathParameters(),    // Path Parameter
        docs.getQueryParameters()))  // Query Parameter
    .pathParam("id", 1L)
    .queryParam("validate", "true")
    .queryParam("notify", "false")
    .body(request)
.when()
    .put("/api/users/{id}")
.then()
    .statusCode(200);
```

---

### Recipe 4: 에러 응답 문서화

다양한 에러 상황을 체계적으로 문서화하는 방법입니다.

#### 🔧 구현 방법

**1단계: 에러 응답 정의**

```java
public class UserCreateDocs extends BaseDocs {
    // 기본 설정...

    public ErrorResponseDocs getErrorResponses() {
        return errorResponseBuilder()
            .addErrorResponse(HttpStatus.BAD_REQUEST, ValidationErrorResponse.class,
                "입력 데이터 검증 실패")
            .addErrorResponse(HttpStatus.CONFLICT, ErrorResponse.class,
                "이미 존재하는 사용자명")
            .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class,
                "서버 내부 오류");
    }
}
```

**2단계: 에러 케이스 테스트**

```java
@Test
void testCreateUserValidationError() throws Exception {
    UserCreateDocs docs = new UserCreateDocs();
    ErrorResponseDocs errorDocs = docs.getErrorResponses();

    given(this.documentationSpec)
        .filter(errorDocs.buildErrorFilter("user-create", HttpStatus.BAD_REQUEST))
        .contentType("application/json")
        .body(invalidRequest)
    .when()
        .post("/api/users")
    .then()
        .statusCode(400);
}
```

#### 💡 핵심 포인트
- HTTP 상태 코드별로 에러 응답 정의
- `buildErrorFilter()`로 에러 케이스 문서화
- 성공/실패 케이스 모두 체계적으로 관리

---

### Recipe 5: List Response

페이징된 목록 응답을 문서화하는 방법입니다.

#### 🔧 구현 방법

```java
public class UserListDocs extends BaseDocs {
    @Override
    public String getIdentifier() {
        return "user-list";
    }

    @Override
    public String getDescription() {
        return "사용자 목록을 조회합니다. 페이징과 필터링을 지원합니다.";
    }

    @Override
    public String getSummary() {
        return "사용자 목록 조회 API";
    }

    // 🎯 List Response 문서화
    @Override
    public Optional<Class<?>> getListResponseClass() {
        return Optional.of(UserResponse.class);
    }

    public QueryParametersSnippet getQueryParameters() {
        return paramBuilder()
            .param("name", "검색할 사용자명", true)
            .param("status", UserStatus.class)  // Enum 자동 문서화
            .param("page", "페이지 번호 (기본값: 0)", true)
            .param("size", "페이지 크기 (기본값: 10)", true)
            .buildQueryParameters();
    }
}
```

#### 💡 핵심 포인트
- `getListResponseClass()`로 리스트 응답 타입 지정
- `Page<T>` 구조가 자동으로 문서화
- Enum 타입 파라미터 자동 문서화

---

## 🔍 고급 활용 팁

### 1. 조건부 Response 처리

```java
@Override
public Optional<Class<?>> getResponseClass() {
    // 단일 응답이 있는 경우
    return Optional.of(UserResponse.class);
}

@Override
public Optional<Class<?>> getListResponseClass() {
    // 리스트 응답이 있는 경우 (우선순위 높음)
    return Optional.of(UserResponse.class);
}
```

### 2. 에러 응답 체인

```java
public ErrorResponseDocs getErrorResponses() {
    return errorResponseBuilder()
        .addErrorResponse(HttpStatus.BAD_REQUEST, ValidationErrorResponse.class, "검증 실패")
        .addErrorResponse(HttpStatus.NOT_FOUND, ErrorResponse.class, "리소스 없음")
        .addErrorResponse(HttpStatus.CONFLICT, ErrorResponse.class, "중복 데이터")
        .addErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.class, "서버 오류");
}
```

### 3. 복합 파라미터 문서화

```java
// Path + Query + Form Parameter 모두 사용
given(this.documentationSpec)
    .filter(docs.successFilter())
    .filter(document("complex-api",
        docs.getPathParameters(),
        docs.getQueryParameters(),
        docs.getFormParameters()))
    // 파라미터 설정...
```

## 🚀 실행 방법

### 1. 개발 서버 실행

```bash
cd easy-rest-redocly-example
./gradlew bootRun
```

### 2. 테스트 실행

```bash
./gradlew test
```

### 3. 문서 생성

```bash
./gradlew redocGenerationIntegration
```

생성된 문서는 `src/main/resources/static/docs/` 에서 확인할 수 있습니다.

## 📁 프로젝트 구조

```
easy-rest-redocly-example/
├── src/
│   ├── main/java/com/example/demo/
│   │   ├── EasyRestRedoclyExampleApplication.java
│   │   ├── controller/
│   │   │   ├── UserController.java      # 기본 CRUD API
│   │   │   ├── AuthController.java      # Form Parameter API
│   │   │   ├── ProductController.java   # 복합 데이터 API
│   │   │   ├── FileController.java      # 파일 업로드 API
│   │   │   └── ErrorController.java     # 에러 처리 API
│   │   ├── dto/
│   │   │   ├── user/                   # 사용자 관련 DTO
│   │   │   ├── auth/                   # 인증 관련 DTO
│   │   │   ├── product/                # 상품 관련 DTO
│   │   │   ├── file/                   # 파일 관련 DTO
│   │   │   ├── error/                  # 에러 응답 DTO
│   │   │   └── common/                 # 공통 Enum 등
│   │   └── docs/
│   │       ├── user/                   # 사용자 API 문서화 클래스들
│   │       ├── auth/                   # 인증 API 문서화 클래스들
│   │       ├── product/                # 상품 API 문서화 클래스들
│   │       ├── file/                   # 파일 API 문서화 클래스들
│   │       └── error/                  # 에러 API 문서화 클래스들
│   └── test/java/com/example/demo/
│       └── controller/                 # REST Docs 테스트 클래스들
├── build.gradle.kts
└── README.md
```

## 🎯 학습 순서 추천

1. **Recipe 1 (기본 CRUD)** - 가장 기본적인 사용법
2. **Recipe 5 (List Response)** - 목록 조회 API 패턴
3. **Recipe 2 (Form Parameter)** - Form 데이터 처리
4. **Recipe 3 (복합 Parameter)** - 복잡한 파라미터 조합
5. **Recipe 4 (에러 응답)** - 체계적인 에러 처리

각 Recipe는 독립적으로 학습 가능하며, 실제 프로젝트의 요구사항에 맞게 조합하여 사용할 수 있습니다.

## 🤝 기여하기

새로운 Recipe나 개선사항이 있으시면 언제든 기여해 주세요!

1. Fork the repository
2. Create your feature branch
3. Add your recipe example
4. Submit a pull request

---

**Happy Documentation! 📚✨**