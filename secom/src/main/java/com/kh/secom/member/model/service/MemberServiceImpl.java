package com.kh.secom.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.exception.DupplicatedUserException;
import com.kh.secom.exception.InvalidParameterException;
import com.kh.secom.exception.MissmatchPasswordException;
import com.kh.secom.member.model.mapper.MemberMapper;
import com.kh.secom.member.model.vo.ChangePasswordDTO;
import com.kh.secom.member.model.vo.Member;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void save(MemberDTO requestMember) { // 일반 사용자 가입 메소드

		// 빈 문자열 확인
		if ("".equals(requestMember.getUserId()) || "".equals(requestMember.getUserPwd())) {
			throw new InvalidParameterException("유효하지 않은 값입니다.");
		}

		// ID 중복체크
		Member searched = memberMapper.findByUserId(requestMember.getUserId());
		if (searched != null) {
			throw new DupplicatedUserException("중복된 아이디 입니다.");
		}

		// 비밀번호 암호화 + ROLE 컬럼에 USER 라고 저장
		Member member = Member.builder().userId(requestMember.getUserId())
				.userPwd(passwordEncoder.encode(requestMember.getUserPwd())).role("ROLE_USER").build();

		memberMapper.save(member);

		log.info("가입 성공");
	}
	
	private Long passwordMatches(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new MissmatchPasswordException("비밀번호가 일치하지 않음");
		}
		return userDetails.getUserNo();
	}

	@Override
	public void changePassword(ChangePasswordDTO changeEntity) {
		Long userNo = passwordMatches(changeEntity.getCurrentPwd());
		String encodeNewPassword = passwordEncoder.encode(changeEntity.getNewPwd());
		Map<String, String> changeRequest = new HashMap<String, String>();
		changeRequest.put("userNo", String.valueOf(userNo));
		changeRequest.put("password", encodeNewPassword);
		memberMapper.changePassword(changeRequest);
	}

	@Override
	public void deleteByPassword(String password) {
		Long userNo = passwordMatches(password);
		memberMapper.deleteByPassword(userNo);
	}

}
