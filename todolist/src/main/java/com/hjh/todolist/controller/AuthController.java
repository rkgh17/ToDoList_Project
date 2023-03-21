package com.hjh.todolist.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjh.todolist.auth.AuthInfo;
import com.hjh.todolist.auth.Login;
import com.hjh.todolist.exception.TokenNotFoundException;
import com.hjh.todolist.jwt.AuthorizationExtractor;
import com.hjh.todolist.jwt.TokenManager;
import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.member.MemberRepository;
import com.hjh.todolist.member.dto.MemberRequestDto;
import com.hjh.todolist.member.dto.MemberResponseDto;
import com.hjh.todolist.refreshtoken.RefreshTokenService;
import com.hjh.todolist.service.AuthService;

import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final MemberRepository memberRepository;
	private final RefreshTokenService refreshTokenService;
	private final TokenManager tokenManager;
	
	@PostMapping("/signup")
	public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto){
		try {
			MemberResponseDto responseDto = authService.signup(requestDto);
			return ResponseEntity.ok(responseDto);
		}catch (RuntimeException e) { // 중복된 사용자일시 409 Conflict 반환 - 서버에 존재하는 데이터
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
		return ResponseEntity.ok(authService.login(requestDto));
	}
	
	// 로그인 새로운 버전 - 중단
//	@PostMapping("/login")
//	public ResponseEntity<Void> login(@Valid @RequestBody MemberRequestDto requestDto){
//		AuthInfo authInfo = authService.login(requestDto);
//	}
	
	/**
	 * access token과 refresh token을 담는 헤더가 존재하는지 확인하고 헤더에 담긴 refresh token을 뽑아온 뒤,
	 * refresh token 테이블에 담긴 값과 요청받은 값을 비교한다. 
	 * 비교한 값이 일치하고, refresh token이 만료되지 않았다면 새로운 access token을 발급하여 응답해준다.
	 */
	@GetMapping("/refresh")
	public ResponseEntity<Void> refresh(HttpServletRequest request, @Login AuthInfo authInfo){
		validateExistHeader(request);
		Long memberId = authInfo.getId();
		String refreshToken = AuthorizationExtractor.extractRefreshToken(request);
		
		refreshTokenService.matches(refreshToken, memberId);
		
		String accessToken = tokenManager.createAccessToken(authInfo);
		
		return ResponseEntity.noContent()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.build();
	}
	
	private void validateExistHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String refreshTokenHeader = request.getHeader("refreshToken");
		if(Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
			throw new TokenNotFoundException();
		}
	}

}
