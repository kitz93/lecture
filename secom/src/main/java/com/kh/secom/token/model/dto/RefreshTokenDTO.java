package com.kh.secom.token.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RefreshTokenDTO {
	
	private Long userNo;
	private String token;
	private Long expiredAt;

}
