package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		/*
		 * AutoConfiguration(자동 구성)
		 * 
		 * 스프링 부트의 핵심 기능
		 * 	애플리케이션의 클래스 패스에 존재하는 라이브러리와 설정을 기반으로 Bean을 자동으로 구성해줌
		 * 
		 * @EnableAutoConfiguration
		 * 	스프링 부트의 자동 구성을 활성화 해주는 애노테이션
		 * 	@SpringBootApplication 내부에 포함되어 있기 때문에 직접 작성할 일은 잘 없음
		 * 
		 * @SpringBootApplication
		 * 	스프링 부트 애플리케이션 진입점 클래스에 사용되는 애노테이션
		 * 
		 * 핵심 애노테이션
		 * 	@EnableAutoConfiguration
		 * 	@ComponentScan
		 * 	@Configuration
		 * 
		 * 동작 순서
		 * 	애플리케이션 시작 -> @SpringBootApplication 애노테이션이 붙은 클래스의 main 메소드에서 run을 호출
		 * 	-> 자동 구성 활성화 -> @EnableAutoConfiguration 애노테이션을 통해 자동 구성을 활성화
		 * 
		 */
		
		SpringApplication.run(DemoApplication.class, args);
	}

}
