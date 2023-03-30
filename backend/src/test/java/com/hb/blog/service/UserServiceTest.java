package com.hb.blog.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hb.blog.domain.Role;
import com.hb.blog.domain.User;
import com.hb.blog.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testJoin() {
        User user = User.builder()
                .userName("test1")
                .password("1234")
                .emailId("test@naver.com")
                .role(Role.USER)
                .build();

        when(userRepository.save(any())).thenReturn(user);

        User result = userService.join(User.builder().userName("test1").password("1234").build());

        verify(userRepository, times(1)).save(any());
        assertThat(result).isEqualTo(user);
    }

}
