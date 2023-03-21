package com.hjh.todolist.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * HTTP 요청에서 Authorization 헤더를 추출하는 기능을 구현한 메서드
 * extract 메서드를 통해 HTTP 요청에서 Authorization 헤더를 추출함.
 *
 */
public class AuthorizationExtractor {
	public static final String BEARER_TYPE = "Bearer";
	
	private AuthorizationExtractor() {
    }
	
    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        return extract(headers);
    }
    
    public static String extractRefreshToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Refresh-Token");
        return extract(headers);
    }
    
    
    /**
     * Authorization(권한) 헤더 추출
     * Bearer {jwt token} 형식으로 되어있는 헤더에서 bearer문자열을 제거하여 실제 jwt 토큰 값을 추출함
     * @param headers
     */
    private static String extract(Enumeration<String> headers) {
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();

                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
