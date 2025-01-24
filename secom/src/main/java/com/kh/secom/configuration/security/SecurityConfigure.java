package com.kh.secom.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kh.secom.auth.util.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {
	
	private final JwtFilter filter;

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
				.cors(AbstractHttpConfigurer::disable) // 지금은 사용하지 않고 나중에 nginx 붙이기
				.authorizeHttpRequests(requests -> {
					requests.requestMatchers("/members", "/members/login").permitAll(); // 인증없이 이용 가능
					requests.requestMatchers(HttpMethod.PUT, "/members").authenticated(); // 인증해야 이용 가능
					requests.requestMatchers("/admin/**").hasRole("ADMIN"); // admin 계정만 이용 가능
				})
				/*
				 * sessionManagement : 세션 관리에 대한 설정을 지정 sessionCreationPolicy : 세션에 대한 정책을 설정
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
