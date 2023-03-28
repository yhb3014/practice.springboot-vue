package com.hb.blog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.config.jwt.TokenInfo;
import com.hb.blog.dto.LoginDto;
import com.hb.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto) {
        TokenInfo tokenInfo = userService.login(loginDto);

        return tokenInfo;
    }
}
