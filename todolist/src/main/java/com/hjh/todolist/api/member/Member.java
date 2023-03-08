package com.hjh.todolist.api.member;

import lombok.Getter;
import lombok.ToString;


// 도메인 - 엔티티

@Getter
@ToString
public class Member {
	
	private int num;
	private String username;
	private String id;
	private String passwd;
	
//	@Enumerated(EnumType.STRING)
	private String role = "ROLE_MEMBER";

}
