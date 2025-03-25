package com.example.easyrestredoclylib.core;

public class DocsConfigRegistry {

	private static DocsDefaultConfig config = new DefaultDocsConfig(); // 기본값

	public static void set(DocsDefaultConfig customConfig) {
		if (customConfig == null) throw new IllegalArgumentException("Config는 null일 수 없습니다.");
		config = customConfig;
	}

	public static DocsDefaultConfig get() {
		return config;
	}
}

