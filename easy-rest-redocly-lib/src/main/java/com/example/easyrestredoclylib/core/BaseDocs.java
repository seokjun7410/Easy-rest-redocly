package com.example.easyrestredoclylib.core;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import java.util.Optional;

public abstract class BaseDocs {

	private final RestAssuredRestDocumentationBuilder builder;
	private final DocsDefaultConfig config;

	public BaseDocs() {
		config = DocsConfigRegistry.get();

		builder = new RestAssuredRestDocumentationBuilder(getIdentifier(), getDescription(), getSummary())
			.requestHeader(config.globalDefaultHeaderSpec());

		// Add request documentation if present
		getRequestClass().ifPresent(requestClass -> builder.addRequest(requestClass, config));

		// Add response documentation - prioritize list response over single response
		if (getListResponseClass().isPresent()) {
			builder.addListResponse(getListResponseClass().get());
		} else {
			getResponseClass().ifPresent(builder::addResponse);
		}
	}

	public abstract String getIdentifier();

	public abstract String getDescription();

	public abstract String getSummary();

	public Optional<Class<?>> getRequestClass(){
		return Optional.empty();
	}

	public Optional<Class<?>> getResponseClass() {
		return Optional.empty();
	}

	public Optional<Class<?>> getListResponseClass() {
		return Optional.empty();
	}


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

	public FormParamBuilder formParamBuilder() {
		return new FormParamBuilder();
	}

	public ErrorResponseDocs errorResponseBuilder() {
		return new ErrorResponseDocs();
	}

}