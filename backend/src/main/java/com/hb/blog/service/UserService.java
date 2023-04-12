package com.hb.blog.service;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hb.blog.config.jwt.JwtTokenProvider;
import com.hb.blog.config.jwt.TokenInfo;
import com.hb.blog.domain.RefreshToken;
import com.hb.blog.domain.User;
import com.hb.blog.dto.LoginDto;
import com.hb.blog.repository.RefreshTokenRepository;
import com.hb.blog.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * 
     * TODO
     * 1. Validation
     * 
     * @param user
     * @return
     */
    public User join(User user) {
        if (!checkDuplicateUser(user)) {
            String encPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encPassword);
            User saveUser = userRepository.save(user);
            return saveUser;
        }

        return null;
    }

    /**
     * JWT 로그인
     * 
     * @param loginDto
     * @return
     */
    public TokenInfo login(LoginDto loginDto) {
        User loginUser = getUserByUserName(loginDto.getUserName());
        String emailId = loginUser.getEmailId();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserName(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // accessToken, refreshToken 발급
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // refreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(tokenInfo.getRefreshToken())
                .tokenKey(emailId)
                .build();
        if (refreshTokenRepository.existsByTokenKey(emailId)) {
            // 기존 refreshToken 삭제
            refreshTokenRepository.deleteByTokenKey(emailId);
        } else {
            refreshTokenRepository.save(refreshToken);
        }

        return tokenInfo;
    }

    public String getNewAccessToken(HttpServletRequest request) {
        String bearerToken = jwtTokenProvider.resolveToken(request);
        HashMap<String, String> payloadMap = jwtTokenProvider.getPayloadByToken(bearerToken);
        String userName = payloadMap.get("sub");

        User user = getUserByUserName(userName);
        RefreshToken refreshToken = getRefreshTokenByTokenKey(user.getEmailId());

        String newAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken.getRefreshToken(),
                user.getUserName());

        return newAccessToken;
    }

    public User getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("?"));

        return user;
    }

    public User getUserByEmailId(String emailId) {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("?"));

        return user;
    }

    public boolean checkDuplicateUser(User user) {

        return userRepository.existsByUserName(user.getUserName());
    }

    public RefreshToken getRefreshToken(String refreshToken) {

        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("?"));
    }

    public RefreshToken getRefreshTokenByTokenKey(String tokenKey) {

        return refreshTokenRepository.findByTokenKey(tokenKey).orElseThrow(() -> new RuntimeException("?"));
    }
}
