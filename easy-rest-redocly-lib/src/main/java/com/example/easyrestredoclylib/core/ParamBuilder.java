package com.example.easyrestredoclylib.core;

import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import java.util.ArrayList;
import java.util.List;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;
import org.springframework.restdocs.request.RequestDocumentation;

public class ParamBuilder {

	List<ParamDescription> params = new ArrayList<>();

	public ParamBuilder param(String name, String description) {
		params.add(new ParamDescription(name, description));
		return this;
	}

	public ParamBuilder param(String name, String description, boolean optional) {
		ParamDescription paramDesc = new ParamDescription(name, description);
		paramDesc.setOptional(optional);
		params.add(paramDesc);
		return this;
	}

	public QueryParametersSnippet buildQueryParameters() {
		ParameterDescriptor[] parameterDescriptors = getParameterDescriptors();
		return queryParameters(parameterDescriptors);
	}

	public PathParametersSnippet buildPathParameters() {
		ParameterDescriptor[] parameterDescriptors = getParameterDescriptors();
		return pathParameters(parameterDescriptors);
	}

	protected ParameterDescriptor[] getParameterDescriptors() {
		return params.stream()
			.map(each -> {
				ParameterDescriptor descriptor = RequestDocumentation.parameterWithName(each.paramFieldName())
					.description(each.description());
				if (each.isOptional()) {
					descriptor = descriptor.optional();
				}
				return descriptor;
			}).toArray(ParameterDescriptor[]::new);
	}

	public ParamBuilder param(String param, Class<? extends Enum<?>> enumClass) {

		Enum<?>[] enumConstants = enumClass.getEnumConstants();

		List<String> constantNames = new ArrayList<>();
		if (enumConstants == null) {
			throw new NullPointerException("enumClass가 null 입니다.");
		}

		for (Enum<?> constant : enumConstants) {
			String constantName = constant.name();
			// Now you can use constantName as needed
			constantNames.add(constantName);
			System.out.println("Enum Constant Name: " + constantName);
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enum : ");

		if (constantNames.size() == 1) {
			stringBuilder.append(constantNames.get(0));
		} else {
			for (int i = 0; i < constantNames.size() - 1; i++) {
				stringBuilder.append(constantNames.get(i)).append(", ");
			}
			stringBuilder.append(constantNames.get(constantNames.size()-1));
		}

		params.add(new ParamDescription(param, stringBuilder.toString()));
		return this;
	}
}
