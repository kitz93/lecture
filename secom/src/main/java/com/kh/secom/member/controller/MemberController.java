package com.kh.secom.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.ChangePasswordDTO;
import com.kh.secom.member.model.vo.LoginResponse;
import com.kh.secom.member.model.vo.MemberDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "members", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final AuthenticationService authService;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody MemberDTO requestMember) {
		// log.info("요청한 사용자의 데이터 : {}", requestMember);
		memberService.save(requestMember);
		return ResponseEntity.ok("회원 가입 성공");
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@Valid @RequestBody MemberDTO requestMember) {

		// 로그인 성공 시 AccessToken / RefreshToken 발행
		Map<String, String> tokens = authService.login(requestMember);
		LoginResponse response = LoginResponse.builder().username(requestMember.getUserId()).tokens(tokens).build();

		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody ChangePasswordDTO changeEntity){
		log.info("{}", changeEntity);
		memberService.changePassword(changeEntity);
		return ResponseEntity.ok("비밀번호를 수정했습니다.");
	}

}
