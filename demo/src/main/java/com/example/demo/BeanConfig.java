package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
	
	/*
	 * Java 기반 설정을 통해 사용해야 하는 Bean을 정의할 수 있음
	 * 
	 * @Configiuration
	 * 	스프링의 설정 클래스를 정의할 때 사용
	 * 	하나 이상의 @Bean 애노테이션이 달린 메소드를 포함해 스프링 컨테이너에 Beanb을 등록함
	 * 
	 * @Bean
	 * 	@Configuration 애노테이션이 달린 클래스 내에서 메소드에 적용되어 스프링 Bean을 생성하고 관리
	 *	메소드 반환값이 스프링 컨테이너에 의해 Bean으로 등록됨.
	 *
	 * 이로 인해 XML 설정보다 빠른 시점에 오류를 발견할 수 있고, 코드 기반이기 때문에 자동 완성이나 수정이 용이하고
	 * 설정 클래스 내에서 Bean의 생성과정을 명확하게 정의할 수 있음
	 */
	
	@Bean
	public TestBean testBean() {
		return new TestBean();
	}

}
