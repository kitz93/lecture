package com.kh.secom.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.token.model.dto.RefreshTokenDTO;
import com.kh.secom.token.model.mapper.TokenMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JwtUtil jwt;
	private final TokenMapper tokenMapper;

	@Override
	public Map<String, String> generateToken(String username, Long userNo) {

		// 1. AccessToken 만들기
		// 2. RefreshToken 만들기
		Map<String, String> tokens = createTokens(username);
		// 3. RefreshToken DB에 저장
		saveToken(tokens.get("refreshToken"), userNo);
		// 4. 만료기간이 지난 RefreshToken 지우기

		// 5. 사용자가 RefreshToken 증명하려 할 때 DB에서 조회

		return tokens;
	}

	private Map<String, String> createTokens(String username) {
		String accessToken = jwt.getAccessToken(username);
		String refreshToken = jwt.getRefressToken(username);
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		return tokens;
	}

	private void saveToken(String refreshToken, Long userNo) {
		RefreshTokenDTO token = RefreshTokenDTO.builder().token(refreshToken).userNo(userNo)
				.expiredAt(System.currentTimeMillis() + 3600000L * 72).build();
		tokenMapper.saveToken(token);
	}

}
