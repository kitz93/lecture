package com.kh.secom.board.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDTO {
	
	private Long boardNo;
	@NotBlank(message = "제목 입력")
	private String boardTitle;
	@NotBlank(message = "내용 입력")
	private String boardContent;
	@NotBlank(message = "작성자 입력")
	private String boardWriter;
	private String boardFileUrl;
	
}
