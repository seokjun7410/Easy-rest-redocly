package easyrest.redocly

import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.yaml.snakeyaml.Yaml
import java.io.File

class EasyRestRedoclyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.lifecycle("✅ EasyRestRedoclyPlugin applied!")

        // 문서 관련 의존성 자동 추가
        project.dependencies.apply {
            add("testImplementation", "org.springframework.restdocs:spring-restdocs-mockmvc")
            add("testImplementation", "org.springframework.restdocs:spring-restdocs-restassured")
            add("testImplementation", "com.epages:restdocs-api-spec-mockmvc:0.19.0")
            add("testImplementation", "com.epages:restdocs-api-spec-restassured:0.19.0")
        }

        // snippetsDir 전역 설정
        project.extensions.extraProperties["snippetsDir"] = project.file("build/generated-snippets")

        // openapi3 실행 task (epages가 내부에서 정의하는 task)
        val makeOAS = project.tasks.register("makeOAS") {
            group = "automatic_documentation"
            dependsOn("openapi3") // ✅ 여기서 실행 순서만 보장
        }

        // fix + redoc bundle task
        val fixAndBundle = project.tasks.register("fixAndBundle") {
            group = "automatic_documentation"
            dependsOn(makeOAS)

            doLast {
                val inputFile = project.file("build/api-spec/openapi.yaml")          // ✅ 루트 기준으로 경로 고정
                val outputFile = project.file("openapi-fixed.yaml")                  // ✅ 루트 기준으로 경로 고정

                if (!inputFile.exists()) {
                    project.logger.error("❌ openapi.yaml not found at ${inputFile.absolutePath}")
                    return@doLast
                }

                val yaml = Yaml()
                val inputContent = inputFile.readText(Charsets.UTF_8)
                val data = yaml.load<Any>(inputContent)

                fixExamples(data)

                outputFile.writeText(yaml.dump(data), Charsets.UTF_8)

            }
        }


        // redoc 단독 실행 task (optional)
        project.tasks.register("redocGenerationIntegration", Exec::class.java) {
            group = "automatic_documentation"
            dependsOn(fixAndBundle)

            val fixedFile = project.file("openapi-fixed.yaml") // ✅ 사용자 프로젝트 기준 경로
            commandLine("/opt/homebrew/bin/redoc-cli", "bundle", fixedFile.absolutePath)
        }

    }

    // 예제 JSON 수정 로직
    private fun fixExamples(res: Any?) {
        if (res is Map<*, *>) {
            res.forEach { (key, value) ->
                if (value is Map<*, *>) {
                    fixExamples(value)
                }

                if (key == "application/json;charset=UTF-8" || key == "application/json") {
                    if (value is Map<*, *> && value.containsKey("examples")) {
                        val examples = value["examples"] as? Map<*, *> ?: return
                        examples.forEach { (_, content) ->
                            val contentMap = content as? MutableMap<*, *> ?: return@forEach
                            val rawValue = contentMap["value"] as? String ?: return@forEach

                            try {
                                val parsed = JsonSlurper().parseText(rawValue)
                                (contentMap as MutableMap<String, Any>)["value"] = parsed
                            } catch (e: Exception) {
                                // 무시
                            }
                        }
                    }
                }
            }
        }
    }
}
