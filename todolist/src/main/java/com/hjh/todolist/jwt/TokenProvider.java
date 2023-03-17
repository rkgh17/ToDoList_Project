package com.hjh.todolist.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.service.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 만료시간 30분
	private final Key key;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14; // 14일	
	private final CustomUserDetailsService customUserDetailsService;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey, CustomUserDetailsService customUserDetailsService) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.customUserDetailsService = customUserDetailsService;
	}
	
	// 토큰 생성
	public TokenDto generateTokenDto(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = (new Date().getTime());
		
		Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
		
		System.out.println(tokenExpiresIn);
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.setExpiration(tokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
		String refreshToken = generateRefreshToken(authentication.getName());
		
		return TokenDto.builder()
				.grantType(BEARER_TYPE)
				.accessToken(accessToken)
				.tokenExpiresIn(tokenExpiresIn.getTime())
				.refreshToken(refreshToken)
				.refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
				.build();
	}
	
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		
		if(claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal,"",authorities);
	}
		
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		}catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		}catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		}catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
	
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		}catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
	
	//
	/**
	 * refreshToken 생성 
	 * userid를 받아 해당 userid를 subject로 갖는 jwt 생성. 유효기간 14일
	 *  
	 * @param userid
	 * @return
	 */
	public String generateRefreshToken(String userid) {
		Claims claims = Jwts.claims().setSubject(userid);
		Date now = new Date();
		Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
	
	/**
	 * refreshToken을 사용하여 인증(Authentication )
	 * refreshToken을 받아 해당 refreshToken에서 subject(userid)정보를 추출하고 이를 이용하여
	 * customUserDetailsService에서 해당 userid의 UserDetails를 조회하여
	 * 새로운 UsernamePasswordAuthenticationToken 객체 생성.
	 * 
	 * @param refreshToken
	 * @return
	 */
	public Authentication getAuthenticationFromRefreshToken(String refreshToken) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(refreshToken)
				.getBody();
		
		String userid = claims.getSubject();
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userid);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
