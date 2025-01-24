package com.kh.secom.member.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
public class ChangePasswordDTO {
	
	@NotBlank(message="현재 비밀번호를 입력해주세요.")
	private String currentPwd;
	
	@NotBlank(message="현재 비밀번호를 입력해주세요.")
	private String newPwd;
	
}
