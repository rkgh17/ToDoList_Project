package com.hjh.todolist.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.hjh.todolist.user.Authority;
import com.hjh.todolist.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String username;
	private String password;
	private String nickname;
	private Set<Authority> authorityDtoSet;
	
	public static UserDto from(User user) {
		if(user == null) return null;
		
		return UserDto.builder()
				.username(user.getUsername())
				.nickname(user.getNickname())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(authority -> Authority.builder().authorityName(authority.getAuthorityName()).build())
						.collect(Collectors.toSet()))
				.build();
	}

}
