package com.hb.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.config.jwt.TokenInfo;
import com.hb.blog.domain.User;
import com.hb.blog.dto.LoginDto;
import com.hb.blog.dto.ResponseDto;
import com.hb.blog.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * 
     * @param user
     * @return
     */
    @PostMapping("/join")
    public ResponseDto<User> join(@RequestBody User user) {

        return new ResponseDto<User>(HttpStatus.OK.value(), userService.join(user));
    }

    /**
     * 로그인
     * 
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public ResponseDto<TokenInfo> login(@RequestBody LoginDto loginDto) {

        return new ResponseDto<TokenInfo>(HttpStatus.OK.value(), userService.login(loginDto));
    }

    /**
     * accessToken 재발급
     * 
     * @param tokenInfo
     * @return
     */
    @PostMapping("/refresh")
    public ResponseDto<String> refresh(HttpServletRequest request) {

        // return new ResponseDto<String>(HttpStatus.OK.value(),
        // userService.getNewAccessToken(tokenInfo));
        return new ResponseDto<String>(HttpStatus.OK.value(), userService.getNewAccessToken(request));
    }
}
