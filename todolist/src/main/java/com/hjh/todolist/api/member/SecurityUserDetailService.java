//package com.hjh.todolist.api.member;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SecurityUserDetailService implements UserDetailsService{
//	
//	@Autowired
//	private MemberRepository memberRepository;
//	
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		Optional<Member> optional = memberRepository.findbyEmail(username);
////		if(!optional.isPresent()) {
////			throw new UsernameNotFoundException(username + " 사용자 없음");
////		}else {
////			Member member = optional.get();
////			return new SecurityUser(member);
////		}
////	}
//	
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
////		return null;
////		
////
////	}
//	
//	
//}