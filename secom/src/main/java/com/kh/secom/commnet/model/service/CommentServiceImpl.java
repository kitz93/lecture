package com.kh.secom.commnet.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.service.BoardService;
import com.kh.secom.commnet.model.dto.CommentDTO;
import com.kh.secom.commnet.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper commentMapper;
	private final BoardService boardService;
	private final AuthenticationService authService;

	@Override
	public void insertComment(CommentDTO comment) {
		boardService.findById(comment.getRefBoardNo());
		CustomUserDetails user = authService.getAuthenticationUser();
		authService.validWriter(comment.getCommentWriter(), user.getUsername());
		comment.setCommentWriter(String.valueOf(user.getUserNo()));
		commentMapper.insertComment(comment);
	}

	@Override
	public List<CommentDTO> findById(Long boardNo) {
		return commentMapper.findById(boardNo);
	}

}
