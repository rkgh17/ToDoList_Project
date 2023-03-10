package com.hjh.todolist.jwt;

/**
 * JWT 관련 - 상수로 사용되는 파일
 * JWT 토큰에서 Header에 키 값으로 사용되는 authorization 값과 
 * 클라이언트에서 JWT로 전송을 할 때 사용하는 “BEARER” 값을 상수로 정의하였습니다.
 */

public final class AuthConstants {
	public static final String AUTH_HEADER = "Authorization";
	public static final String TOKEN_TYPE = "BEARER";
}
