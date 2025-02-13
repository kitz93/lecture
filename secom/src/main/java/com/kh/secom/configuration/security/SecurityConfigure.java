package com.kh.secom.configuration.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.secom.auth.util.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {
	
	private final JwtFilter filter;
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 요청 보내는 URL 허가
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 요청 메소드 허가 
		corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 헤더 키값 허가
		corsConfiguration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

	@Bean // Bean 애노테이션을 이용해서 Bean 등록할 시 동일한 이름의 메소드가 존재하면 안됨
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// return httpSecurity.formLogin().disable().build();
		/*
		 * return httpSecurity.formLogin(new
		 * Customizer<FormLoginConfigurer<HttpSecurity>>() {
		 * 
		 * @Override public void customize(FormLoginConfigurer<HttpSecurity> formLogin)
		 * { formLogin.disable(); } }).httpBasic(null).csrf(null).cors(null).build();
		 */
		return httpSecurity.formLogin(AbstractHttpConfigurer::disable) // formLogin 방식을 사용하지 않음
				.httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용하지 않음
				.csrf(AbstractHttpConfigurer::disable) // csrf(Cross-Site Request Forgery) 사용하지 않음
				.cors(Customizer.withDefaults()) // 지금은 사용하지 않고 나중에 nginx 붙이기
				.authorizeHttpRequests(requests -> {
					requests.requestMatchers("/members", "/members/login", "/uploads/**").permitAll(); // 인증없이 이용 가능
					requests.requestMatchers(HttpMethod.PUT, "/members").authenticated(); // 인증해야 이용 가능
					requests.requestMatchers("/admin/**").hasRole("ADMIN"); // admin 계정만 이용 가능
					requests.requestMatchers(HttpMethod.DELETE, "/members").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/members/refresh").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/boards").authenticated();
					requests.requestMatchers(HttpMethod.GET, "/boards/**", "/comments/**").permitAll();
					requests.requestMatchers(HttpMethod.PUT, "/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.DELETE, "/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/comments").authenticated();
				})
				/*
				 * sessionManagement : 세션 관리에 대한 설정을 지정 
				 * sessionCreationPolicy : 세션에 대한 정책을 설정
				 */
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
