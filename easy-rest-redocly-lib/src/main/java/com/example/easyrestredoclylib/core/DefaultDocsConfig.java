package com.example.easyrestredoclylib.core;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class DefaultDocsConfig implements DocsDefaultConfig {

	@Override
	public RequestHeadersSnippet globalDefaultHeaderSpec() {
		return requestHeaders(
			headerWithName(HttpHeaders.CONTENT_TYPE).optional().description("Content Type Header"));
	}

	@Override
	public ResponseFieldsSnippet defaultExceptionResponseSpec() {
		return responseFields(fieldWithPath("timestamp").description("에러 발생 시간"))
			.and(fieldWithPath("status").description("HTTP 상태 코드"))
			.and(fieldWithPath("error").description("에러 유형"))
			.and(fieldWithPath("path").description("요청 경로"));
	}

	@Override
	public String descriptionFormatting(List<String> constraints) {
		if (constraints.isEmpty()) return "";
		if (constraints.size() == 1) return "[" + constraints.get(0) + "]";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < constraints.size() - 1; i++) {
			sb.append("[").append(constraints.get(i)).append("], ");
		}
		sb.append("[").append(constraints.get(constraints.size() - 1)).append("]");
		return sb.toString();
	}
}
