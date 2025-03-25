package com.example.easyrestredoclylib.core;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public abstract class BaseDocs {

	private final RestAssuredRestDocumentationBuilder builder;
	private final DocsDefaultConfig config;

	public BaseDocs() {
		config = DocsConfigRegistry.get();

		if (getRequestClass() != null && getResponseClass() != null) {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec())
				.addRequest(getRequestClass(),config)
				.addResponse(getResponseClass());
		} else if (getRequestClass() != null && getListResponseClass() != null) {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec())
				.addRequest(getRequestClass(),config)
				.addListResponse(getListResponseClass());
		} else if (getListResponseClass() != null) {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec())
				.addListResponse(getListResponseClass());
		} else if (getResponseClass() != null) {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec())
				.addResponse(getResponseClass());
		} else if (getRequestClass() != null) {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec())
				.addRequest(getRequestClass(),config);
		}   else {
			builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(),
				getSummary())
				.requestHeader(config.globalDefaultHeaderSpec());
		}
	}

	public abstract String getIdentifier();

	public abstract String getDescription();

	public abstract String getSummary();

	public Class<?> getRequestClass(){
		return null;
	}

	public abstract Class<?> getResponseClass();
	public abstract Class<?> getListResponseClass();


	public RestDocumentationFilter successFilter() {
		return builder.build();
	}

	public RestDocumentationFilter validationFilterInRestAssured(String identifier) {
		return RestAssuredRestDocumentationWrapper.document(identifier, getDescription(), getSummary(),
			config.defaultExceptionResponseSpec());
	}

	public RestDocumentationResultHandler validationFilterInMockMvc(String identifier) {
		return MockMvcRestDocumentationWrapper.document(identifier, getDescription(), getSummary(),
			config.defaultExceptionResponseSpec());
	}

	public RestAssuredRestDocumentationBuilder docsBuilder() {
		return builder;
	}

	public RequestHeadersSnippet defaultHeader() {
		return config.globalDefaultHeaderSpec();
	}

	public ParamBuilder paramBuilder() {
		return new ParamBuilder();
	}

}