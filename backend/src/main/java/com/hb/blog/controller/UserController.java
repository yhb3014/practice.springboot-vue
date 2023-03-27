package com.hb.blog.controller;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.config.auth.PrincipalDetailsService;
import com.hb.blog.domain.User;
import com.hb.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PrincipalDetailsService principalDetailsService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> param) {
        User user = userService.getUserByEmailId((String) param.get("emailId"));
        return "test";
    }
}
