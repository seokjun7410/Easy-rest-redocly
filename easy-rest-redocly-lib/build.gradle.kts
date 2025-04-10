plugins {
    id("java-library")
    id("maven-publish")

}

group = "com.example"
version = "0.0.6-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring REST Docs (MockMvc 기반)
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")

    // JUnit Jupiter (JUnit 5)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation ("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation ("com.epages:restdocs-api-spec-mockmvc:0.18.2")
    implementation ("com.epages:restdocs-api-spec-restassured:0.18.2")
    implementation ("org.springframework.restdocs:spring-restdocs-restassured:3.0.1")

    implementation("org.springframework.boot:spring-boot-autoconfigure:3.4.4")

}


// 테스트용 JAR 만들기 (라이브러리를 테스트 코드로만 구성할 경우)
val testJar by tasks.registering(Jar::class) {
    archiveClassifier.set("tests")
    from(sourceSets["test"].output)
}

artifacts {
    add("archives", testJar)
}

// Maven Local 배포용 설정
publishing {
    publications {
//        testImplementation("com.easy-rest:easy-rest-redocly:0.0.5-SNAPSHOT:LTS")
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(testJar.get()) {
                classifier = "LTS"
            }
            groupId = "com.easy-rest"
            artifactId = "easy-rest-redocly"
            version = "0.0.6-SNAPSHOT"
        }
    }
    repositories {
        mavenLocal()
    }
}


// 테스트는 JUnit 5로 실행
tasks.test {
    useJUnitPlatform()
}