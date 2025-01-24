package com.kh.secom.member.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

	private Long userNo;

	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문자 또는 숫자만 사용 가능합니다.")
	@Size(min = 5, max = 15, message = "아이디는 최소 5자, 최대 15자까지 입력 가능합니다.")
	@NotBlank(message = "아이디는 반드시 입력해야 합니다.")
	private String userId;

	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 영문자 또는 숫자만 사용 가능합니다.")
	@Size(min = 4, max = 20, message = "비밀번호는 최소 4자, 최대 20자까지 입력 가능합니다.")
	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	private String userPwd;

	private String role;
}
