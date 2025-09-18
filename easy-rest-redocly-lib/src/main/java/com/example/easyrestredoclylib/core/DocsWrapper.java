package com.example.easyrestredoclylib.core;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.QueryParametersSnippet;
import org.springframework.restdocs.request.FormParametersSnippet;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class DocsWrapper {


	public RequestFieldsSnippet buildRequestFieldSnippet(Class<?> requestClass, DocsDefaultConfig config) {
		List<FieldDescriptor> descriptors = buildFieldDescriptors("", requestClass, config);
		return requestFields(descriptors.toArray(new FieldDescriptor[0]));
	}

	private List<FieldDescriptor> buildFieldDescriptors(String parentPath, Class<?> clazz, DocsDefaultConfig config) {
		List<FieldDescriptor> result = new ArrayList<>();

		// ConstraintDescriptions를 통해 제약 조건을 추출 (예시는 기존 로직)
		ConstraintDescriptions userConstraints = new ConstraintDescriptions(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldPath = parentPath.isEmpty() ? field.getName() : parentPath + field.getName();
			List<String> constraints = new ArrayList<>(userConstraints.descriptionsForProperty(field.getName()));

			DocsDescription descriptionAnnotation = field.getAnnotation(DocsDescription.class);
			if (descriptionAnnotation != null) {
				if (!StringUtils.isBlank(descriptionAnnotation.value())) {
					constraints.add(descriptionAnnotation.value());
				}
			}

			// Enum의 경우 모든 상수를 추가
			Class<?> type = field.getType();
			if (type.isEnum()) {
				Object[] enumConstants = type.getEnumConstants();
				for (Object enumConstant : enumConstants) {
					constraints.add(((Enum<?>) enumConstant).name());
				}
			}

			// 기본 FieldDescriptor 생성
			FieldDescriptor descriptor = fieldWithPath(fieldPath)
				.description(config.descriptionFormatting(constraints));

			// DocsDescription에 nullable 설정이 있다면 optional로 표시
			if (descriptionAnnotation != null && descriptionAnnotation.nullable()) {
				descriptor = descriptor.optional();
			}
			result.add(descriptor);

			// 내부 객체 또는 Collection의 경우 재귀적으로 내부 필드 문서화 처리
			if (field.getAnnotation(Valid.class) != null) {
				if (isCollection(type)) {
					// Collection 타입이면 제네릭 타입의 클래스를 추출
					Type genericType = field.getGenericType();
					if (genericType instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) genericType;
						Type[] typeArgs = pt.getActualTypeArguments();
						if (typeArgs.length > 0 && typeArgs[0] instanceof Class) {
							Class<?> innerClass = (Class<?>) typeArgs[0];
							// Collection은 배열 형태로 문서화 ("필드명[].내부필드")
							List<FieldDescriptor> nestedDescriptors = buildFieldDescriptors(fieldPath + "[].", innerClass, config);
							result.addAll(nestedDescriptors);
						}
					}
				} else if (!isSimpleType(type)) {
					// 단순타입이 아니라면 재귀적으로 내부 필드 처리 ("필드명.내부필드")
					List<FieldDescriptor> nestedDescriptors = buildFieldDescriptors(fieldPath + ".", type, config);
					result.addAll(nestedDescriptors);
				}
			}
		}
		return result;
	}

	// 간단한 타입(primitive, Wrapper, String, 기본 java 타입 등)을 체크하는 유틸 메서드
	private boolean isSimpleType(Class<?> type) {
		return type.isPrimitive()
			|| type.equals(String.class)
			|| Number.class.isAssignableFrom(type)
			|| type.equals(Boolean.class)
			|| type.equals(Character.class)
			|| type.getName().startsWith("java.time")
			|| type.getName().startsWith("java.lang")
			|| type.isEnum();
	}

	// Collection 타입 확인
	private boolean isCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}


	public ResponseFieldsSnippet buildResponseFieldSnippet(Class<?> responseClass) {
		List<FieldDescriptor> responseDescriptions = getFieldDescriptors("", responseClass);
		return responseFields(responseDescriptions.toArray(new FieldDescriptor[0]));
	}

	public ResponseFieldsSnippet buildListResponseFieldSnippet(Class<?> responseClass) {
		List<FieldDescriptor> responseDescriptions = getListFieldDescriptors("", responseClass,0);
		return responseFields(responseDescriptions.toArray(FieldDescriptor[]::new));
	}

	private List<FieldDescriptor> getFieldDescriptors(String prefix, Class<?> responseClass) {
		return getFieldDescriptors(prefix, responseClass, 0);
	}

	private List<FieldDescriptor> getFieldDescriptors(String prefix, Class<?> responseClass, int depth) {
		if (depth > 5) {
			return new ArrayList<>();
		}

		Field[] fields = responseClass.getDeclaredFields();
		List<FieldDescriptor> responseDescriptions = new ArrayList<>();
		for (Field field : fields) {
			DocsDescription descriptionAnnotation = field.getAnnotation(DocsDescription.class);
			String fieldName = "";
			String descriptionValue = "";

			Class<?> fieldType = field.getType();
			Package packagePath = field.getType().getPackage();
			if (packagePath == null || packagePath.getName().startsWith("java") || fieldType.isEnum()) {
				fieldName = prefix + field.getName();
				if (descriptionAnnotation != null) {
					descriptionValue = descriptionAnnotation.value();
				}
				responseDescriptions.add(fieldWithPath(fieldName).description(descriptionValue));
			} else {
				List<FieldDescriptor> fieldDescriptors = getFieldDescriptors(field.getName() + ".",
					fieldType, depth + 1);
				responseDescriptions.addAll(fieldDescriptors);
			}
		}
		return responseDescriptions;
	}

	private List<FieldDescriptor> getListFieldDescriptors(String prefix, Class<?> responseClass,int depth) {
		if (depth > 5) {
			return new ArrayList<>();
		}
		Field[] fields = responseClass.getDeclaredFields();
		List<FieldDescriptor> responseDescriptions = new ArrayList<>();
		for (Field field : fields) {


			DocsDescription descriptionAnnotation = field.getAnnotation(DocsDescription.class);
			String fieldName = "";
			String descriptionValue = "";

			Class<?> fieldType = field.getType();
			Package packagePath = field.getType().getPackage();

			String typeName = field.getType().getTypeName();
			String packagePathName = null;
			fieldName = prefix + field.getName();
			if (packagePath != null) {
				packagePathName = packagePath.getName();
			}
			if (typeName.contains("List")) {
				packagePathName = getRootPackage(field);
				fieldType = getRootFieldType(field);
			}
			if (packagePath == null || (packagePathName.startsWith("java")) || fieldType.isEnum()) {
				fieldName = "[]." + fieldName;
				if (descriptionAnnotation != null) {
					descriptionValue = descriptionAnnotation.value();
				}
				if (descriptionAnnotation != null) {
					if (descriptionAnnotation.nullable()) {
						responseDescriptions.add(
							fieldWithPath(fieldName).optional().description(descriptionValue)
						);
					}else{
						responseDescriptions.add(
							fieldWithPath(fieldName).description(descriptionValue));
					}
				}else {
					responseDescriptions.add(
						fieldWithPath(fieldName).description(descriptionValue));
				}

			} else {
				List<FieldDescriptor> fieldDescriptors = getListFieldDescriptors(
					field.getName() + "[].", fieldType,depth+1);
				responseDescriptions.addAll(fieldDescriptors);
			}
		}
		return responseDescriptions;
	}

	private Class<?> getRootFieldType(Field listField) {

		// 필드가 리스트 타입인지 확인합니다.
		if (List.class.isAssignableFrom(listField.getType())) {
			// 리스트 타입의 제네릭 타입을 얻어옵니다.
			Type genericType = listField.getGenericType();

			if (genericType instanceof ParameterizedType) {
				// 제네릭 타입이 매개변수화된 타입인 경우
				ParameterizedType parameterizedType = (ParameterizedType) genericType;

				// 제네릭 타입의 실제 타입 인자를 가져옵니다.
				Type[] typeArguments = parameterizedType.getActualTypeArguments();

				for (Type typeArgument : typeArguments) {
					if (typeArgument instanceof Class) {
						// 실제 타입 인자가 클래스인 경우
						return (Class<?>) typeArgument;
					}
				}
			}
		}
		return null;
	}

	private String getRootPackage(Field listField) {
		if (List.class.isAssignableFrom(listField.getType())) {
			// 리스트 타입의 제네릭 타입을 얻어옵니다.
			Type genericType = listField.getGenericType();

			if (genericType instanceof ParameterizedType) {
				// 제네릭 타입이 매개변수화된 타입인 경우
				ParameterizedType parameterizedType = (ParameterizedType) genericType;

				// 제네릭 타입의 실제 타입 인자를 가져옵니다.
				Type[] typeArguments = parameterizedType.getActualTypeArguments();

				for (Type typeArgument : typeArguments) {
					// 실제 타입 인자의 패키지 정보를 출력합니다.
					return ((Class<?>) typeArgument).getPackage().getName();
				}
			}
		}
		return null;
	}

	public RestDocumentationFilter buildRestDocumentationFilter(
		String identifier,
		String description,
		RequestHeadersSnippet requestHeadersSnippet,
		String summary,
		RequestFieldsSnippet requestFieldsSnippet,
		ResponseFieldsSnippet responseFieldsSnippet,
		QueryParametersSnippet queryParametersSnippet,
		PathParametersSnippet pathParametersSnippet,
		FormParametersSnippet formParametersSnippet) {

		// Build list of non-null snippets
		List<Snippet> snippets = new ArrayList<>();
		snippets.add(requestHeadersSnippet);

		if (requestFieldsSnippet != null) {
			snippets.add(requestFieldsSnippet);
		}
		if (responseFieldsSnippet != null) {
			snippets.add(responseFieldsSnippet);
		}
		if (queryParametersSnippet != null) {
			snippets.add(queryParametersSnippet);
		}
		if (pathParametersSnippet != null) {
			snippets.add(pathParametersSnippet);
		}
		if (formParametersSnippet != null) {
			snippets.add(formParametersSnippet);
		}

		return RestAssuredRestDocumentationWrapper.document(
			identifier,
			description,
			summary,
			snippets.toArray(new Snippet[0])
		);
	}

	// Keep the old method for backward compatibility
	public RestDocumentationFilter buildRestDocumentationFilter(
		String identifier,
		String description,
		RequestHeadersSnippet requestHeadersSnippet,
		String summary,
		RequestFieldsSnippet requestFieldsSnippet,
		ResponseFieldsSnippet responseFieldsSnippet,
		QueryParametersSnippet queryParametersSnippet,
		PathParametersSnippet pathParametersSnippet) {

		return buildRestDocumentationFilter(
			identifier, description, requestHeadersSnippet, summary,
			requestFieldsSnippet, responseFieldsSnippet,
			queryParametersSnippet, pathParametersSnippet, null
		);
	}

	// Legacy complex method - keeping for compatibility but marking as deprecated
	@Deprecated
	public RestDocumentationFilter buildRestDocumentationFilterLegacy(
		String identifier,
		String description,
		RequestHeadersSnippet requestHeadersSnippet,
		String summary,
		RequestFieldsSnippet requestFieldsSnippet,
		ResponseFieldsSnippet responseFieldsSnippet,
		QueryParametersSnippet queryParametersSnippet,
		PathParametersSnippet pathParametersSnippet) {

		if (requestFieldsSnippet != null && responseFieldsSnippet != null) {
			if (queryParametersSnippet != null && pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					responseFieldsSnippet,
					queryParametersSnippet,
					pathParametersSnippet
				);
			} else if (queryParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					responseFieldsSnippet,
					queryParametersSnippet
				);
			} else if (pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					responseFieldsSnippet,
					pathParametersSnippet
				);
			} else {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					responseFieldsSnippet
				);
			}
		} else if (requestFieldsSnippet != null) {
			if (queryParametersSnippet != null && pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					queryParametersSnippet,
					pathParametersSnippet
				);
			} else if (queryParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					queryParametersSnippet
				);
			} else if (pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet,
					pathParametersSnippet
				);
			} else {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					requestFieldsSnippet
				);
			}
		} else if (responseFieldsSnippet != null) {
			if (queryParametersSnippet != null && pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					responseFieldsSnippet,
					queryParametersSnippet,
					pathParametersSnippet
				);
			} else if (queryParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					responseFieldsSnippet,
					queryParametersSnippet
				);
			} else if (pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					responseFieldsSnippet,
					pathParametersSnippet
				);
			} else {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					requestHeadersSnippet,
					responseFieldsSnippet
				);
			}
		} else {
			if (queryParametersSnippet != null && pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					queryParametersSnippet,
					pathParametersSnippet
				);
			} else if (queryParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					queryParametersSnippet
				);
			} else if (pathParametersSnippet != null) {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary,
					pathParametersSnippet
				);
			} else {
				return RestAssuredRestDocumentationWrapper.document(identifier,
					description,
					summary
				);
			}
		}
	}
}