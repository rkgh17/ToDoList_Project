package com.hjh.todolist.refreshtoken;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjh.todolist.exception.InvalidRefreshTokenException;
import com.hjh.todolist.jwt.TokenManager;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenManager tokenManager;
	
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, TokenManager tokenManager) {
    	this.refreshTokenRepository = refreshTokenRepository;
    	this.tokenManager = tokenManager;
	}
    
    // 리프레쉬 토큰 db저장
    @Transactional
    public void saveToken(String token, Long memberId) {
        deleteToken(memberId);
        RefreshToken refreshToken = new RefreshToken(memberId, token);
        refreshTokenRepository.save(refreshToken);
    }
    
    // 리프레쉬 토큰 검사
    @Transactional
    public void matches(String refreshToken, Long memberId) {
    	RefreshToken savedToken = refreshTokenRepository.findByMemberId(memberId)
    			.orElseThrow(InvalidRefreshTokenException :: new);
    	
    	if(!tokenManager.isValid(savedToken.getToken())) {
    		refreshTokenRepository.delete(savedToken);
    		throw new InvalidRefreshTokenException();
    	}
    	savedToken.validateSameToken(refreshToken);
    }
    
    // db 저장 전 삭제
    @Transactional
    public void deleteToken(Long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }

}
