package com.hjh.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT 토큰 생성의 기반
 * @author comss
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	
	private String username;
	private String password;

}
