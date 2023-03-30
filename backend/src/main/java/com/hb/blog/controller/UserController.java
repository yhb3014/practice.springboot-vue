package com.hb.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.config.jwt.TokenInfo;
import com.hb.blog.domain.User;
import com.hb.blog.dto.LoginDto;
import com.hb.blog.service.UserService;

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
    public ResponseEntity<User> join(@RequestBody User user) {

        return new ResponseEntity<>(userService.join(user), HttpStatus.OK);
    }

    /**
     * 로그인
     * 
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }
}
