package com.kh.secom.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.service.BoardService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;

	@PostMapping
	public ResponseEntity<?> save(@ModelAttribute @Valid BoardDTO board,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		service.save(board, file);
		return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 완료");
	}

	@GetMapping
	public ResponseEntity<List<BoardDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page) {
		List<BoardDTO> list = service.findAll(page);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardDTO> findById(
			@PathVariable(name = "id") @Min(value = 1, message = "유효하지 않은 게시글 번호") Long boardNo) {
		BoardDTO board = service.findById(boardNo);
		return ResponseEntity.ok(board);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BoardDTO> update(@PathVariable(name = "id") @Min(value = 1, message = "유효하지 않은 게시글 번호") Long boardNo,
			@ModelAttribute @Valid BoardDTO board, @RequestParam(name = "file", required = false) MultipartFile file) {
		board.setBoardNo(boardNo);
		BoardDTO updated = service.update(board, file);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") @Min(value = 1, message = "유효하지 않은 게시글 번호") Long boardNo) {
		service.deleteById(boardNo);
		return ResponseEntity.ok("삭제 완료");
	}

}
