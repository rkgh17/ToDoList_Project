package com.hjh.todolist.service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjh.todolist.auth.AuthInfo;
import com.hjh.todolist.jwt.TokenProvider;
import com.hjh.todolist.jwt.dto.TokenDto;
import com.hjh.todolist.member.Member;
import com.hjh.todolist.member.MemberRepository;
import com.hjh.todolist.member.dto.MemberRequestDto;
import com.hjh.todolist.member.dto.MemberResponseDto;
import com.hjh.todolist.refreshtoken.RefreshToken;
import com.hjh.todolist.refreshtoken.RefreshTokenRepository;
import com.hjh.todolist.refreshtoken.RefreshTokenService;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
	private final AuthenticationManagerBuilder managerBuilder;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	
	public MemberResponseDto signup(MemberRequestDto requestDto) {
		if(memberRepository.existsByEmail(requestDto.getEmail())) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다");
		}
		
		Member member = requestDto.toMember(passwordEncoder);
		return MemberResponseDto.of(memberRepository.save(member));
	}
	
	public TokenDto login(MemberRequestDto requestDto) {
		
		UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
				
		Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
		
		// 반환할 토큰
		TokenDto tokens = tokenProvider.generateTokenDto(authentication);
	
		// 리프레시토큰 db에 저장
		refreshTokenService.saveToken(tokens.getRefreshToken(), memberRepository.findyByNativeQuery(requestDto.getEmail()));
		
		return tokens; 
	}
	
	// 로그인 로직 변경 - 중단
//	public AuthInfo login(MemberRequestDto requestDto) {
//		String username = requestDto.getNickname();
//		String email = requestDto.getEmail();
//		
//		Optional<Member> member = memberRepository.findByEmail(email);
//		return new AuthInfo(member.get)
//	}
}
