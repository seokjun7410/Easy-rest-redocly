package com.example.easyrestredoclylib.core;

import java.util.Objects;

public final class ParamDescription {

	private final String paramFieldName;
	private final String description;

	ParamDescription(String paramFieldName, String description) {
		this.paramFieldName = paramFieldName;
		this.description = description;
	}

	public String paramFieldName() {
		return paramFieldName;
	}

	public String description() {
		return description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (ParamDescription) obj;
		return Objects.equals(this.paramFieldName, that.paramFieldName) &&
			Objects.equals(this.description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(paramFieldName, description);
	}

	@Override
	public String toString() {
		return "ParamDescription[" +
			"paramFieldName=" + paramFieldName + ", " +
			"description=" + description + ']';
	}


}
