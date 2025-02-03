package com.kh.secom.commnet.model.service;

import java.util.List;

import com.kh.secom.commnet.model.dto.CommentDTO;

public interface CommentService {
	
	void insertComment(CommentDTO comment);
	
	List<CommentDTO> findById(Long boardNo);

}
