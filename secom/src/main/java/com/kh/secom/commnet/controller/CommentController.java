package com.kh.secom.commnet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.commnet.model.dto.CommentDTO;
import com.kh.secom.commnet.model.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService service;
	
	@PostMapping
	public ResponseEntity<String> insertComment(@Valid @RequestBody CommentDTO comment) {
		service.insertComment(comment);
		return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성 완료");
	}
	
	@GetMapping("/{boardNo}")
	public ResponseEntity<List<CommentDTO>> findById(@PathVariable(name="boardNo")Long boardNo) {
		List<CommentDTO> comments = service.findById(boardNo);
		return ResponseEntity.ok(comments);
	}

}
