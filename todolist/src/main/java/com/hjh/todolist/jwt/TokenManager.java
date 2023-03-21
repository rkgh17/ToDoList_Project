package com.hjh.todolist.jwt;

import com.hjh.todolist.auth.AuthInfo;

public interface TokenManager {
	String createAccessToken(AuthInfo authInfo);
	boolean isValid(String token);
}
