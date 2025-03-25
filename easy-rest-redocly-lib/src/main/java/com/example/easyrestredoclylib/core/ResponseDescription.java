package com.example.easyrestredoclylib.core;

import java.util.Objects;

public final class ResponseDescription {

	private final String responseFieldName;
	private final String description;

	ResponseDescription(String responseFieldName, String description) {
		this.responseFieldName = responseFieldName;
		this.description = description;
	}

	public String responseFieldName() {
		return responseFieldName;
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
		var that = (ResponseDescription) obj;
		return Objects.equals(this.responseFieldName, that.responseFieldName) &&
			Objects.equals(this.description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(responseFieldName, description);
	}

	@Override
	public String toString() {
		return "ResponseDescription[" +
			"responseFieldName=" + responseFieldName + ", " +
			"description=" + description + ']';
	}


}
