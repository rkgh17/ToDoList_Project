package com.hjh.todolist.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.member.MemberRepository;
import com.hjh.todolist.member.dto.MemberRequestDto;
import com.hjh.todolist.member.dto.MemberResponseDto;
import com.hjh.todolist.service.AuthService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final MemberRepository memberRepository;
	
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

}
