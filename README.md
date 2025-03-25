# 📘 easy-rest-redocly
- easy-rest-redocly는 REST Docs 문서를 리플렉션 기반으로 쉽게 작성할 수 있도록 도와주며, 작성된 문서를 Redocly-UI를 통해 가독성 높게 렌더링해주는 도구입니다.
- [easy-rest-redocly를 이용해 생성한 API 문서 예시](https://seokjun7410.github.io/e-commerce/redoc-2402151414.html)

# ✨ easy-rest-redocly 사용 시 장점

- 리플렉션 기반 자동 문서화로 코드가 간결해집니다.
- 복잡한 DTO와 Enum 타입 필드까지 자동으로 문서화됩니다.
- 유지보수가 쉬워집니다.
- 자동으로 Redoc 기반의 예쁜 HTML 문서를 제공합니다.

# ✨ 예시

다음은 많은 필드를 가진 복잡한 DTO와 Enum 타입 필드를 문서화할 때 순수 REST Docs와 easy-rest-redocly의 차이를 보여줍니다.

## REST Docs VS easy-rest-redocly

```java
// REST DOCS
.filter(document("user-create",
                requestFields(
                    fieldWithPath("username").description("사용자 이름"),
                    fieldWithPath("email").description("이메일 주소"),
                    fieldWithPath("password").description("비밀번호"),
                    fieldWithPath("age").description("나이").optional(),
                    fieldWithPath("address.street").description("도로명"),
                    fieldWithPath("address.city").description("도시"),
                    fieldWithPath("address.zipCode").description("우편번호"),
                    fieldWithPath("preferences.notifications").description("알림 수신 여부"),
                    fieldWithPath("preferences.marketing").description("마케팅 정보 수신 여부"),
                    fieldWithPath("role").description("사용자 역할 (USER, ADMIN)")
                ),
                responseFields(
                    fieldWithPath("id").description("생성된 사용자 ID"),
                    fieldWithPath("username").description("사용자 이름"),
                    fieldWithPath("email").description("이메일 주소"),
                    fieldWithPath("createdAt").description("생성 일시"),
                    fieldWithPath("preferences.notifications").description("알림 설정 상태"),
                    fieldWithPath("preferences.marketing").description("마케팅 설정 상태"),
                    fieldWithPath("role").description("사용자 역할 (USER, ADMIN)")
                )
            ))

// easy-rest-redocly
.filter(new UserCreateDocs().successFilter())
```

### easy-rest-redocly 명세 방법
```java
//문서(Docs)클래스로 명세를 대체합니다.
public class UserCreateDocs extends BaseDocs {

    @Override
    public String getIdentifier() { return "user-create"; }

    @Override
    public String getDescription() { return "새로운 사용자를 생성합니다."; }

    @Override
    public String getSummary() { return "사용자 생성 API"; }

    @Override
    public Class<?> getRequestClass() { return UserCreateRequest.class; }

    @Override
    public Class<?> getResponseClass() { return UserCreateResponse.class; }
}
```

### UserCreateRequest.java
```java
//DTO 계층에서 필드에 대한 명세를 작성합니다.
public class UserCreateRequest {

    @DocsDescription("사용자 이름")
    private String username;

    @DocsDescription("이메일 주소")
    private String email;

    @DocsDescription("비밀번호")
    private String password;

    @DocsDescription(value = "나이", nullable = true)
    private Integer age;

    @DocsDescription("주소 정보")
    private Address address;

    @DocsDescription("사용자 선호 설정")
    private Preferences preferences;

    @DocsDescription("사용자 역할")
    private UserRole role;
}
```

### UserCreateResponse.java
```java
//DTO 계층에서 필드에 대한 명세를 작성합니다.
public class UserCreateResponse {

    @DocsDescription("생성된 사용자 ID")
    private Long id;

    @DocsDescription("사용자 이름")
    private String username;

    @DocsDescription("이메일 주소")
    private String email;

    @DocsDescription("생성 일시")
    private LocalDateTime createdAt;

    @DocsDescription("사용자 선호 설정")
    private Preferences preferences;

    @DocsDescription("사용자 역할")
    private UserRole role;
}

public enum UserRole {
    USER, ADMIN
}
```


# 🚀 설치 방법

### 1. JitPack 레포지토리 추가

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

### 2. 라이브러리 의존성 추가

```groovy
testImplementation 'com.github.seokjun7410.easy-restdocs:easy-rest-redocly:v0.0.5'
```


## 🧪 플러그인 사용 예시 (build.gradle)

```groovy
buildscript {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.seokjun7410.easy-restdocs:easy-rest-redocly-plugin:v0.0.5'
    }
}

plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.4'
    id 'io.spring.dependency-management' version '1.1.7'
    id 'com.epages.restdocs-api-spec' version '0.19.0' // easy-rest-redocly는 epages.restdocs-api-spec에 gradle task를 사용하기 때문에 필수적으로 사용해야 합니다.
}

apply plugin: 'io.github.seokjun7410.easy-rest-redocly'
```

> `build.gradle`에 플러그인 적용 시 주의: `plugins {}` 블럭보다 `buildscript {}`가 먼저 와야 합니다.


## 🔧 사용 가능한 태스크

| 태스크 | 설명                                    |
|--------|---------------------------------------|
| `makeOAS` | com.epages.restdocs-api-spec의 `openapi3` 태스크 실행 |
| `fixAndBundle` | epages + redocly 연동 시 String 형식 손상 정리 + `redoc-cli bundle` 실행 |
| `redocGenerationIntegration` | redocly API Doc 생성 (`makeOAS` + `fixAndBundle`)   |


## 🧰 Redoc CLI 설치 방법

이 플러그인의 일부 기능은 `redoc-cli`를 필요로 합니다. 아래 명령어로 전역 설치해주세요.

```bash
npm install -g redoc-cli
```

### 설치 확인

```bash
redoc-cli --version
```

### 수동 실행 예시

```bash
redoc-cli bundle openapi-fixed.yaml
```

# ✨주요 클래스 및 기능

### 📌 1. `BaseDocs` 클래스
`BaseDocs`는 문서를 작성할 때 공통적으로 사용되는 기능들을 제공합니다.

- **기본 설정**: REST 문서를 작성하기 위한 기본 정보를 설정할 수 있습니다.
- **자동 빌드**: 요청/응답 클래스 지정 여부에 따라 자동으로 문서화 필터를 빌드합니다.

#### 사용 예시
```java
public class UserDocs extends BaseDocs {

    @Override
    public String getIdentifier() { return "user-get"; }

    @Override
    public String getDescription() { return "사용자 정보를 조회합니다."; }

    @Override
    public String getSummary() { return "사용자 조회 API"; }

    @Override
    public Class<?> getResponseClass() { return UserResponse.class; }

    @Override
    public Class<?> getListResponseClass() { return null; }
}
```

### 📌 2. `ParamBuilder` 클래스
`ParamBuilder`를 이용하면 Query Parameter 및 Path Variable 문서화를 쉽게 정의할 수 있습니다.

#### 사용 예시
```java
ParamBuilder paramBuilder = new ParamBuilder()
    .param("userId", "조회할 사용자 ID")
    .param("status", UserStatus.class); // Enum 타입 파라미터 정의 가능

// Query Parameter로 빌드
QueryParametersSnippet querySnippet = paramBuilder.buildQueryParameters();

// Path Variable로 빌드
PathParametersSnippet pathSnippet = paramBuilder.buildPathParameters();
```

### 📌 3. Custom Annotation: `@DocsDescription`
필드나 클래스에 직접 설명을 추가하여 문서화에 반영할 수 있습니다.

#### 사용 예시
```java
public class UserResponse {

    @DocsDescription("사용자 고유 번호")
    private Long id;

    @DocsDescription(value = "사용자 이름", nullable = true)
    private String name;
}
```
### 📌 4. jakarta.validation.constraints 제약조건 문서화

이 라이브러리는 Spring REST Docs와 `javax.validation` 또는 `jakarta.validation`의 제약조건 어노테이션(`@NotNull`, `@Size` 등)을 리플렉션으로 자동 인식하여 문서화합니다.

아래 코드 예시는 필드에 정의된 제약조건을 자동으로 읽어 설명 문서에 추가하는 기능을 보여줍니다:

#### DTO 예시
```java
public class UserRequest {

    @DocsDescription("사용자 이름")
    @NotNull
    @Size(min=2, max=30)
    private String username;
}
```

#### 자동으로 생성된 문서 설명 예시
```
사용자 이름 [Must not be null], [Size must be between 2 and 30]
```

### 🚩 5. Enum  문서화

easy-rest-redocly는 Enum 타입 필드의 사용 가능한 값과 같은 제약사항을 자동으로 문서화에 포함합니다. 또한 사용자는 이러한 제약사항의 형식을 자유롭게 커스터마이징 할 수 있습니다.

기본 형식은 다음과 같습니다:
```java
[USER], [ADMIN]
```

### 🚩 6. Enum 및 jakarta.validation.constraints 포맷 커스터마이징
사용자는 `DocsDefaultConfig` 인터페이스를 구현하여 원하는 형태로 변경할 수 있습니다:

```java
public class MyCustomDocsConfig implements DocsDefaultConfig {
    @Override
    public String descriptionFormatting(List<String> constraints) {
        if (constraints.isEmpty()) return "";
        if (constraints.size() == 1) return "Possible values: " + constraints.get(0);

        return "Possible values: " + String.join(", ", constraints);
    }

    // 나머지 메서드 구현
}
```

```java
@Configuration
public class DocsConfig {

    @Bean
    public DocsDefaultConfig customDocsConfig(){
        return new MyCustomDocsConfig();
    }
}
```
### 7. 자동 설정 (`EasyRestDocsAutoConfiguration`)
Spring Boot의 자동 설정으로, 기본 문서화 설정이 자동 구성됩니다. 별도의 추가 설정 없이도 사용 가능합니다.



## 필수 설정 및 주의사항
- 각 문서화 클래스는 반드시 `BaseDocs`를 상속받아 구현해야 합니다.


## 📝 License

MIT License 기반입니다. epages/restdocs-api-spec 또한 MIT 라이선스를 따릅니다.





