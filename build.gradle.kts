// 루트 프로젝트의 build.gradle.kts

// 루트에서는 플러그인 선언 거의 없음
// 하위 모듈에서 각각 필요에 따라 선언
plugins {
    // 비워두거나, 공통 도구(예: dependency updates)만 선언
}

group = "io.github.yourusername"
version = "1.0.0"

// 모든 프로젝트에서 사용할 리포지토리
allprojects {
    repositories {
        mavenCentral()
    }
}

// 공통 설정 (예: Java 버전, Kotlin 설정 등)
subprojects {
    // 모든 하위 모듈에 공통 적용 (원할 경우)
    plugins.withType<JavaPlugin> {
        the<JavaPluginExtension>().apply {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
