package com.hjh.todolist.exception;

public class TokenNotFoundException extends UnauthorizedException {
	
	private static final String MESSAGE = "토큰이 존재하지 않습니다.";

	public TokenNotFoundException() {
		super(MESSAGE);
	}
}