package com.hb.blog.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hb.blog.config.jwt.JwtTokenProvider;
import com.hb.blog.config.jwt.TokenInfo;
import com.hb.blog.domain.User;
import com.hb.blog.dto.LoginDto;
import com.hb.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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
     * TODO
     * 1. refreshToken
     * 
     * @param loginDto
     * @return
     */
    public TokenInfo login(LoginDto loginDto) {
        User loginUser = getUserByEmailId(loginDto.getEmailId());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserName(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
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
}
