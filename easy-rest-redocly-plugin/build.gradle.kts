plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.seokjun7410"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:1.33")
    implementation("org.codehaus.groovy:groovy-json:3.0.9")
}

gradlePlugin {
    plugins {
        create("easyRestRedocly") {
            id = "io.github.seokjun7410.easy-rest-redocly"
            implementationClass = "easyrest.redocly.EasyRestRedoclyPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("${project.rootDir}/repo")
        }
    }
}