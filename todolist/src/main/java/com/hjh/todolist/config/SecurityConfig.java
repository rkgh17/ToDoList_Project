package com.hjh.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.hjh.todolist.jwt.JwtAccessDeniedHandler;
import com.hjh.todolist.jwt.JwtAuthenticationEntryPoint;
import com.hjh.todolist.jwt.JwtSecurityConfig;
import com.hjh.todolist.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public SecurityConfig(
			TokenProvider tokenProvider,
			CorsFilter corsFilter,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}
	
	// 비밀번호 암호화
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		// token을 사용하는 방식이기 때문에 csrf disable
		.csrf().disable()
		
		.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
		
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.accessDeniedHandler(jwtAccessDeniedHandler)
		
		
		// 세션 사용 X
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and()

		// API 요청 허용 - HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
		.authorizeRequests()
		.antMatchers("/", "/**" ,"/api/hello", "/api/authenticate", "/api/signup").permitAll()
		
		.anyRequest().authenticated()
		
		.and()
		.apply(new JwtSecurityConfig(tokenProvider));

		
		return http.build();
	}
}
