package com.example.easyrestredoclylib.core;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ErrorResponseDocs {

	private final Map<HttpStatus, ErrorResponseSpec> errorSpecs;
	private final DocsDefaultConfig config;

	public ErrorResponseDocs() {
		this.errorSpecs = new HashMap<>();
		this.config = DocsConfigRegistry.get();
	}

	public ErrorResponseDocs addErrorResponse(HttpStatus status, Class<?> errorResponseClass) {
		return addErrorResponse(status, errorResponseClass, null);
	}

	public ErrorResponseDocs addErrorResponse(HttpStatus status, Class<?> errorResponseClass, String description) {
		DocsWrapper docsWrapper = new DocsWrapper();
		ResponseFieldsSnippet responseSnippet = docsWrapper.buildResponseFieldSnippet(errorResponseClass);

		String errorDescription = description != null ? description :
			String.format("Error response for HTTP %d - %s", status.value(), status.getReasonPhrase());

		errorSpecs.put(status, new ErrorResponseSpec(errorDescription, responseSnippet, null));
		return this;
	}

	public ErrorResponseDocs addErrorResponse(HttpStatus status, ResponseFieldsSnippet responseSnippet) {
		return addErrorResponse(status, responseSnippet, null);
	}

	public ErrorResponseDocs addErrorResponse(HttpStatus status, ResponseFieldsSnippet responseSnippet, String description) {
		String errorDescription = description != null ? description :
			String.format("Error response for HTTP %d - %s", status.value(), status.getReasonPhrase());

		errorSpecs.put(status, new ErrorResponseSpec(errorDescription, responseSnippet, null));
		return this;
	}

	public ErrorResponseDocs addErrorResponseWithHeaders(HttpStatus status, Class<?> errorResponseClass, ResponseHeadersSnippet headersSnippet) {
		return addErrorResponseWithHeaders(status, errorResponseClass, headersSnippet, null);
	}

	public ErrorResponseDocs addErrorResponseWithHeaders(HttpStatus status, Class<?> errorResponseClass, ResponseHeadersSnippet headersSnippet, String description) {
		DocsWrapper docsWrapper = new DocsWrapper();
		ResponseFieldsSnippet responseSnippet = docsWrapper.buildResponseFieldSnippet(errorResponseClass);

		String errorDescription = description != null ? description :
			String.format("Error response for HTTP %d - %s", status.value(), status.getReasonPhrase());

		errorSpecs.put(status, new ErrorResponseSpec(errorDescription, responseSnippet, headersSnippet));
		return this;
	}

	public RestDocumentationFilter buildErrorFilter(String identifier, HttpStatus expectedStatus) {
		ErrorResponseSpec spec = errorSpecs.get(expectedStatus);
		if (spec == null) {
			throw new IllegalArgumentException("No error response spec found for status: " + expectedStatus);
		}

		String errorIdentifier = identifier + "-error-" + expectedStatus.value();

		if (spec.getHeadersSnippet() != null) {
			return RestAssuredRestDocumentationWrapper.document(
				errorIdentifier,
				spec.getDescription(),
				"Error Response",
				spec.getResponseSnippet(),
				spec.getHeadersSnippet()
			);
		} else {
			return RestAssuredRestDocumentationWrapper.document(
				errorIdentifier,
				spec.getDescription(),
				"Error Response",
				spec.getResponseSnippet()
			);
		}
	}

	public boolean hasErrorSpec(HttpStatus status) {
		return errorSpecs.containsKey(status);
	}

	private static class ErrorResponseSpec {
		private final String description;
		private final ResponseFieldsSnippet responseSnippet;
		private final ResponseHeadersSnippet headersSnippet;

		public ErrorResponseSpec(String description, ResponseFieldsSnippet responseSnippet, ResponseHeadersSnippet headersSnippet) {
			this.description = description;
			this.responseSnippet = responseSnippet;
			this.headersSnippet = headersSnippet;
		}

		public String getDescription() {
			return description;
		}

		public ResponseFieldsSnippet getResponseSnippet() {
			return responseSnippet;
		}

		public ResponseHeadersSnippet getHeadersSnippet() {
			return headersSnippet;
		}
	}
}