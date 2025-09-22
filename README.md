# easy-rest-redocly
- easy-rest-redocly는 REST Docs 문서를 리플렉션 기반으로 쉽게 작성할 수 있도록 도와주며, 작성된 문서를 Redocly-UI를 통해 가독성 높게 렌더링해주는 도구입니다.
- [document](https://easy-rest-redocly.gitbook.io/easy/)

## REST Docs VS easy-rest-redocly
다음은 많은 필드를 가진 복잡한 DTO와 Enum 타입 필드를 문서화할 때 순수 REST Docs와 easy-rest-redocly의 차이를 보여줍니다.

```java
// 😱 Spring REST Docs - 문자열 지옥
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
```
```java
// 😍 Easy REST Redocly - 단 한 줄!
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


## 🧪 플러그인 적용 예시 (build.gradle)

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

implementation 'com.github.seokjun7410.easy-restdocs:easy-rest-redocly:v0.1.3'

apply plugin: 'io.github.seokjun7410.easy-rest-redocly'
```

> `build.gradle`에 플러그인 적용 시 주의: `plugins {}` 블럭보다 `buildscript {}`가 먼저 와야 합니다.


## 🔧 사용 가능한 태스크

| 태스크 | 설명                                    |
|--------|---------------------------------------|
| `makeOAS` | com.epages.restdocs-api-spec의 `openapi3` 태스크 실행 |
| `fixAndBundle` | epages + redocly 연동 시 String 형식 손상 정리 + `redoc-cli bundle` 실행 |
| `redocGenerationIntegration` | redocly API Doc 생성 (`makeOAS` + `fixAndBundle`)   |


## 필수 설정 및 주의사항
- 각 문서화 클래스는 반드시 `BaseDocs`를 상속받아 구현해야 합니다.


## 📝 License

MIT License 기반입니다. epages/restdocs-api-spec 또한 MIT 라이선스를 따릅니다.





