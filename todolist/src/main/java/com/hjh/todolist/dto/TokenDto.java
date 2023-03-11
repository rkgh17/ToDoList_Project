package com.hjh.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 토큰의 response를 받을 때 사용
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
	String token;
}
