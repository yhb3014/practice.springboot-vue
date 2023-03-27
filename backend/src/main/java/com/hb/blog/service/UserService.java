package com.hb.blog.service;

import org.springframework.stereotype.Service;

import com.hb.blog.domain.User;
import com.hb.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmailId(String emailId) {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("?"));
        return user;
    }
}
