package com.example.easyrestredoclylib.core;

import com.epages.restdocs.apispec.RestDocumentationWrapper;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class RestAssuredRestDocumentationBuilder extends RestDocumentationWrapper {

	private final DocsWrapper docsWrapper = new DocsWrapper();
	private RequestHeadersSnippet requestHeadersSnippet = null;
	private RequestFieldsSnippet requestFieldsSnippet = null;
	private ResponseFieldsSnippet responseFieldsSnippet = null;
	private QueryParametersSnippet queryParametersSnippet = null;
	private PathParametersSnippet pathParametersSnippet = null;

	private final String identifier;
	private final String description;
	private final String summary;

	public RestAssuredRestDocumentationBuilder(String identifier, String description, String summary) {
		this.identifier = identifier;
		this.description = description;
		this.summary = summary;
	}

	public RestAssuredRestDocumentationBuilder requestHeader(RequestHeadersSnippet snippet) {
		requestHeadersSnippet = snippet;
		return this;
	}

	public RestAssuredRestDocumentationBuilder addRequest(Class<?> requestClass, DocsDefaultConfig config) {
		requestFieldsSnippet = docsWrapper.buildRequestFieldSnippet(requestClass,config);
		return this;
	}

	public RestAssuredRestDocumentationBuilder addResponse(Class<?> responseClass) {
		responseFieldsSnippet = docsWrapper.buildResponseFieldSnippet(responseClass);
		return this;
	}

	public RestAssuredRestDocumentationBuilder addListResponse(Class<?> listResponseClass) {
		responseFieldsSnippet = docsWrapper.buildListResponseFieldSnippet(listResponseClass);
		return this;
	}

	public RestAssuredRestDocumentationBuilder addQueryParam(QueryParametersSnippet snippet) {
		queryParametersSnippet = snippet;
		return this;
	}

	public RestAssuredRestDocumentationBuilder addPathVariable(PathParametersSnippet snippet) {
		pathParametersSnippet = snippet;
		return this;
	}

	public RestDocumentationFilter build() {
		if (identifier == null)
			throw new NullPointerException("identifier 이(가) null일 수 없습니다.");
		if (description == null)
			throw new NullPointerException("description 이(가) null일 수 없습니다.");
		if (requestHeadersSnippet == null)
			throw new NullPointerException("requestHeader 이(가) null일 수 없습니다.");
		if (summary == null)
			throw new NullPointerException("summary 이(가) null일 수 없습니다.");

		return docsWrapper.buildRestDocumentationFilter(
			identifier,
			description,
			requestHeadersSnippet,
			summary,
			requestFieldsSnippet,
			responseFieldsSnippet,
			queryParametersSnippet,
			pathParametersSnippet
		);
	}
}
