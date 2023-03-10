package com.hjh.todolist.jwt;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.hjh.todolist.api.member.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

/**
 * JWT 관련된 토큰 Util
 * 
 * 해당 클래스는 JWT에서 사용되는 토큰 관련 유틸들을 관리하는 클래스입니다. 
 * JWT를 생성하거나 유효성을 체크하는 등의 전반적으로 처리되는 기능들을 모아둔 클래스입니다.
 * 
 * 해당 부분 중 private static final String jwtSecretKey로 Secret 키를 지정한 부분이 있습니다. 
 * 예시를 위해서 선언하였지만, 보안적인 이슈를 생각하여 환경파일 내에 넣어두고 별도로 관리하며 사용하는 것을 권장드립니다.
 */
@Log4j2
public class TokenUtils {
	
	// @Value(value = "${custom.jwt-secret-key}")
	private static final String jwtSecretKey = "exampleSecretKey";
	
	/**
	 * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드
	 * 
	 * @param userDto UserDto : 사용자 정보
	 * @return String : 토큰
	 */
	public static String generateJwtToken(Member member) {
		// 사용자 시퀸스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
		JwtBuilder builder = Jwts.builder()
				.setHeader(createHeader())								// header 구성
				.setClaims(createClaims(member))						// payload - claims 구성
				.setSubject(String.valueOf(member.getNum()))			// payload - subject 구성
				.signWith(createSignature(), SignatureAlgorithm.HS256)	// signature 구성
				.setExpiration(createExpiredDate());					// Expired Date 구성
		return builder.compact();
	}
	
	/**
	 * 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
	 * 
	 * @param token String : 토큰
	 * @return String : 사용자 정보
	 */
	public static String paseTokenToUserInfo(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtSecretKey).build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	/**
	 * 유효한 토큰인지 확인 해주는 메서드
	 * 
	 * @param token String  : 토큰
	 * @return boolean      : 유효한지 여부 반환
	 */
	public static boolean isValidToken(String token) {
		try{
			Claims claims = getClaimsFromToken(token);
			
			log.info("expireTime :"+ claims.getExpiration());
			log.info("userId :"+ claims.get("userId"));
			log.info("userNm : "+ claims.get("userNm"));
			
			return true;
		}catch (ExpiredJwtException exception) {
			log.error("Token Expired");
			return false;
		}catch (JwtException exception) {
			log.error("Token Tampered");
			return false;
		}catch (NullPointerException exception) {
			log.error("Token is null");
			return false;
		}
	}
	
	
	/**
	 * Header 내에 토큰을 추출합니다.
	 * 
	 * @param header 헤더
	 * @return String
	 */
	public static String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}
	
	/**
	 * 토큰의 만료기간을 지정하는 함수
	 * 
	 * @return Calendar
	 */
	private static Date createExpiredDate() {
		// 토큰 만료시간
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 8);	// 8시간
		return c.getTime();
	}

	/**
	 * JWT의 '헤더' 값을 생성해 주는 메서드	
	 * @return HashMap<String, Object>
	 */
	private static Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		header.put("redDate", System.currentTimeMillis());
		return header;
	}
	
	/**
	 * 사용자 정보를 기반으로 클래임을 생성해주는 메서드
	 * 
	 * @param Member사용자정보
	 * @return Map<String, Object>
	 */
	private static Map<String, Object> createClaims(Member member) {
		Map<String, Object> claims = new HashMap<>();
		
		log.info("userId :" + member.getId());
		log.info("userNm" + member.getUsername());
		
		claims.put("userId", member.getId());
		claims.put("userNm", member.getUsername());
		return claims;
	}
	
    /**
     * JWT "서명(Signature)" 발급을 해주는 메서드
     *
     * @return Key
     */
	private static Key createSignature() {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
		return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
	}
	
	/**
	 * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
	 * 
	 * @param token : 토큰
	 * @return Claims : Claims
	 */
	private static Claims getClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
//				.parseClaimsJws(token).getBody();
		return Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)).build()
				.parseClaimsJws(token).getBody();
	}
	
	/**
	 * 토큰을 기반으로 사용자 정보를 반환받는 메서드
	 * 
	 * @param token : 토큰
	 * @return String : 사용자 아이디
	 */
	public static String getUserIdFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.get("userId").toString();
	}
}
