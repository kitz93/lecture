package com.kh.secom.board.model.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final FileService fileService;
	private final AuthenticationService authService;
	
	

	@Override
	public void save(BoardDTO board, MultipartFile file) {
		// log.info(" board = {} \n file = {}", board, file);
		CustomUserDetails user = authService.getAuthenticationUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			board.setBoardFileUrl(filePath);
		} else {
			board.setBoardFileUrl(null);
		}
		board.setBoardWriter(String.valueOf(user.getUserNo()));
		boardMapper.save(board);
	}

	@Override
	public List<BoardDTO> findAll(int page) {
		int size = 3;
		RowBounds rowBounds = new RowBounds(page * size , size);
		return boardMapper.findAll(rowBounds);
	}
	
	private BoardDTO getBoardOrThrow(Long boardNo) {
		BoardDTO board = boardMapper.findById(boardNo);
		if(board == null) {
			throw new InvalidParameterException("없는 게시글 번호");
		}
		return board;
	}

	@Override
	public BoardDTO findById(Long boardNo) {
		return getBoardOrThrow(boardNo);
	}

	@Override
	public BoardDTO update(BoardDTO board, MultipartFile file) {
		BoardDTO existingBoard = getBoardOrThrow(board.getBoardNo());
		CustomUserDetails user = authService.getAuthenticationUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());
		existingBoard.setBoardTitle(board.getBoardTitle());
		existingBoard.setBoardContent(board.getBoardContent());
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			existingBoard.setBoardFileUrl(filePath);
		}
		boardMapper.update(existingBoard);
		return existingBoard;
	}

	@Override
	public void deleteById(Long boardNo) {
		BoardDTO existingBoard = getBoardOrThrow(boardNo);
		CustomUserDetails user = authService.getAuthenticationUser();
		authService.validWriter(existingBoard.getBoardWriter(), user.getUsername());
		
		boardMapper.deleteById(boardNo);
	}

}
