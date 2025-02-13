package com.kh.secom.commnet.model.dto;

import java.time.LocalDateTime;

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
public class CommentDTO {
	
	private Long commentNo;
	private Long refBoardNo;
	private String commentWriter;
	@NotBlank(message = "내용이 비었음")
	private String content;
	private LocalDateTime createDate;

}
