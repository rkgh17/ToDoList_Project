package com.hjh.todolist.refreshtoken;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
    	this.refreshTokenRepository = refreshTokenRepository;
	}
    
    // 리프레쉬 토큰 db저장
    @Transactional
    public void saveToken(String token, Long memberId) {
        deleteToken(memberId);
        RefreshToken refreshToken = new RefreshToken(memberId, token);
        refreshTokenRepository.save(refreshToken);
    }
    
    // db 저장 전 삭제
    @Transactional
    public void deleteToken(Long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }

}
