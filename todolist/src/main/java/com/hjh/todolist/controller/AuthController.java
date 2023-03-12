package com.hjh.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.member.dto.MemberRequestDto;
import com.hjh.todolist.member.dto.MemberResponseDto;
import com.hjh.todolist.service.AuthService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto){
		return ResponseEntity.ok(authService.signup(requestDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
		return ResponseEntity.ok(authService.login(requestDto));
	}

}
