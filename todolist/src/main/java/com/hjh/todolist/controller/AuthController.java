package com.hjh.todolist.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

import com.hjh.todolist.exception.TokenNotFoundException;
import com.hjh.todolist.jwt.AuthorizationExtractor;
import com.hjh.todolist.jwt.TokenManager;
import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.member.Member;
import com.hjh.todolist.member.MemberRepository;
import com.hjh.todolist.member.dto.MemberRequestDto;
import com.hjh.todolist.member.dto.MemberResponseDto;
import com.hjh.todolist.refreshtoken.RefreshTokenService;
import com.hjh.todolist.service.AuthService;

import java.util.Objects;
import java.util.Optional;

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
	public ResponseEntity<TokenDto> refresh(HttpServletRequest request){
		validateExistHeader(request);
		
//		String refreshToken = AuthorizationExtractor.extractRefreshToken(request);
		// 위 코드 대체
		String refreshToken = request.getHeader("refreshtoken");
	
//		String accessToken = tokenManager.createAccessToken(authInfo);
//		
//		return ResponseEntity.noContent()
//				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//				.build();
		
//		System.out.println(request.getHeader(HttpHeaders.AUTHORIZATION));

		String accessToken = AuthorizationExtractor.extractAccessToken(request); // 엑세스 토큰
//		System.out.println(accessToken.split("\\.")[1]);
		
		// 엑세스 토큰 payload 부분 복호화
		byte[] decodedBytes = Base64.getDecoder().decode(accessToken.split("\\.")[1]); 
		//		System.out.println(new String(decodedBytes));
		
		// 복호화 정보를 JSONParser로 원하는 정보 (member id)를 추출하여 refreshToken값을 db 안의 값과 비교
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new String(decodedBytes));
			System.out.println(jsonObject.get("sub")); // member id	
			refreshTokenService.matches(refreshToken, Long.parseLong((String) jsonObject.get("sub")));
			System.out.println(refreshToken);
			
			// 로그인 메서드에 필요한 정보 추출
			Optional<Member> member = memberRepository.findById(Long.parseLong((String) jsonObject.get("sub")));
			
			MemberRequestDto requestDto =  MemberRequestDto.builder()
														.email(member.get().getEmail())
														.password(member.get().getPassword())
														.nickname(member.get().getNickname())
														.build();
			System.out.println("사용자 정보 추출");
			System.out.println("닉네임 : " + requestDto.getNickname());
			System.out.println("이메일 : " + requestDto.getEmail());
			System.out.println("비밀번호 : " + requestDto.getPassword());
			
			// 새로운 리프레쉬 토큰과 엑세스 토큰 발급
			return ResponseEntity.ok(authService.login(requestDto));
			
		} catch (ParseException e) {
			System.out.println("토큰이 올바르지 않습니다.");
			e.printStackTrace();
		}
		
		System.out.print("refresh실패");
	
		return null;
	}
	
//	@PostMapping("/refresh")
//	public void refresh(@RequestBody )
	
	private void validateExistHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String refreshTokenHeader = request.getHeader("refreshToken");
		if(Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
			throw new TokenNotFoundException();
		}
	}

}
