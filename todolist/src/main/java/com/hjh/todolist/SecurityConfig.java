package com.hjh.todolist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	private final JwtTokenProvider jwtTokenProvider;

	 
	// 비밀번호 암호화
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.cors().disable()
			.csrf().disable()
			.formLogin().disable()
			.headers().frameOptions().disable();
		
		return http.build();
	}
	
	
//  // 아래는 JWT로그인 삽질
//	// authenticationManager를 Bean 등록
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
//		return authenticationConfiguration.getAuthenticationManager();
//	}
//	 
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//			.httpBasic().disable() // rest api만을 고려하여 기본설정 해제
//			.csrf().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
//		.and()
//			.authorizeRequests() // 요청에 대한 사용 권한
////			.antMatchers("/admin/**").hasRole("ADMIN")
//			.antMatchers("/api/post/**").authenticated()
//			.anyRequest().permitAll() // 나머지 요청 누구나 접근 가능
//		.and()
//			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//
//			
//			
//	}
}
