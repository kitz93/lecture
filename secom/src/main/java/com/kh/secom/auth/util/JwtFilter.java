package com.kh.secom.auth.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwt;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		log.info("우리의 토큰 필터 출동 {}", authorization);

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("authorization이 존재하지 않음");
			filterChain.doFilter(request, response);
			return;
		}
		// 토큰 부분 추출
		String token = authorization.split(" ")[1];
		// 1. 내 비밀키로 만들었는가?
		// 2. 유효기간이 남았는가?
		try {
			Claims claims = jwt.parseJwt(token);
			String username = claims.getSubject();
			// log.info("토큰 주인 아이디 : {}", username);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			// log.info("토큰 주인 정보 : {}", userDetails);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
																										// 유저 객체    비밀번호     유저 ROLE
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// 세부설정 - 사용자의 원격주소, MAC 주소, 세션 ID 등이 포함될 수 있음
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (ExpiredJwtException e) {
			log.info("AccessToken이 만료되었습니다.");
			// throw new AccessTokenExpiredException("토큰이 만료되었습니다.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().print("Expired Token");
			return;
		} catch (JwtException e) {
			log.info("Token 검증에 실패했습니다.");
			// throw new JwtTokenException("유효하지 않은 토큰입니다.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().print("Invalid Token");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
