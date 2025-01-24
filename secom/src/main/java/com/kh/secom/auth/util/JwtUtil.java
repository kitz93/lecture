package com.kh.secom.auth.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	// 애플리케이션 설정 파일(application.properties / applincation.yml)에 정의된 속성의 값을 @Value
	// 애노테이션을 이용해서 값을 주입받을 수 있음
	@Value("${jwt.secret}")
	private String secretKey;
	// javax.crypto.SecretKey 타입의 필드로 JWT 서명에 사용할 수 있음
	private SecretKey key;

	private long ACCESS_TOKEN_EXPIRED = 3600000L * 24; // ms 단위
	private long REFRESH_TOKEN_EXPIRED = 3600000L * 72;

	@PostConstruct // Bean 초기화 시 필요한 추가 설정을 할 수 있음
	public void init() {
		byte[] keyArr = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyArr);
	}

	// 만료일 생성하는 메소드
	private Date buildExpirationDate(long expiredDate) {
		return new Date(System.currentTimeMillis() + expiredDate);
	}

	public String getAccessToken(String username) {
		return Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(buildExpirationDate(ACCESS_TOKEN_EXPIRED)).signWith(key).compact();
				// .expiration(new Date()).signWith(key).compact();
	}

	public String getRefressToken(String username) {
		return Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(buildExpirationDate(REFRESH_TOKEN_EXPIRED)).signWith(key).compact();
	}

	public Claims parseJwt(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}

}
