package com.kh.secom.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleInvalidParameter(InvalidParameterException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(DupplicatedUserException.class)
	public ResponseEntity<?> handlerDupplication(DupplicatedUserException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerArgumentValid(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * List<FieldError> list = e.getBindingResult().getFieldErrors(); for(int i = 0;
		 * i < list.size(); i++) { // log.info("예외가 발생한 필드 명 : {}, 이유 : {} ",
		 * list.get(i).getField(), list.get(i).getDefaultMessage());
		 * errors.put(list.get(i).getField(), list.get(i).getDefaultMessage()); }
		 */
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		;
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(AccessTokenExpiredException.class)
	public ResponseEntity<?> handlerExpiredAccessToken(AccessTokenExpiredException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<?> handlerinvalidToken(JwtTokenException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(MissmatchPasswordException.class)
	public ResponseEntity<?> handlerMissmatchPassword(MissmatchPasswordException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
