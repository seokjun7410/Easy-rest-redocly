package com.example.easyrestredoclylib.core;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;


@AutoConfiguration
public class EasyRestDocsAutoConfiguration extends AbstractTestExecutionListener {

	private final DocsDefaultConfig docsDefaultConfig;

	public EasyRestDocsAutoConfiguration(DocsDefaultConfig docsDefaultConfig) {
		this.docsDefaultConfig = docsDefaultConfig;
	}

	@Override
	public void beforeTestClass(TestContext testContext) {
		// 여기서 딱 한 번만 설정
		DocsConfigRegistry.set(docsDefaultConfig);
		System.out.println("[DocsTestListener] TestDocsConfig 초기화 완료!");
	}

	@Bean
	@ConditionalOnMissingBean
	public DocsDefaultConfig defaultDocsDefaultConfig() {
		// 사용자가 직접 Bean 등록 안했을 경우 기본값 제공
		return new DefaultDocsConfig();
	}
}
