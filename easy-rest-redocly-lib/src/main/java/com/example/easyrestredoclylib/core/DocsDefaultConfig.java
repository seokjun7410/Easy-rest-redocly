package com.example.easyrestredoclylib.core;

import java.util.List;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public interface DocsDefaultConfig {
	RequestHeadersSnippet globalDefaultHeaderSpec();

	ResponseFieldsSnippet defaultExceptionResponseSpec();

	String descriptionFormatting(List<String> constraints);
}