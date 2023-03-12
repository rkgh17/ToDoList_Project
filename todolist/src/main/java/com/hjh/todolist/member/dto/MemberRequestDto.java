package com.hjh.todolist.member.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hjh.todolist.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hjh.todolist.member.RoleType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;
    
    public Member toMember(PasswordEncoder passwordEncoder) {
    	return Member.builder()
    			.email(email)
    			.password(passwordEncoder.encode(password))
    			.nickname(nickname)
    			.roleType(RoleType.ROLE_USER)
    			.build();
    }
    
    public UsernamePasswordAuthenticationToken toAuthentication() {
    	return new UsernamePasswordAuthenticationToken(email, password);
    }
}
