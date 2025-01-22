package com.kh.busan.api.model.vo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO implements Serializable {
	
	private Long foodNo;
	@NotBlank(message="작성자는 필수")
	private String writer;
	private String content;

}
