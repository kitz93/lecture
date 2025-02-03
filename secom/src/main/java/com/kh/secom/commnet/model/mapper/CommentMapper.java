package com.kh.secom.commnet.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.secom.commnet.model.dto.CommentDTO;

@Mapper
public interface CommentMapper {
	
	void insertComment(CommentDTO comment);
	
	List<CommentDTO> findById(Long boardNo);

}
