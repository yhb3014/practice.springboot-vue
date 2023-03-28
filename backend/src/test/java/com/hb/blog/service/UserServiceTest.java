package com.hb.blog.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hb.blog.domain.Role;
import com.hb.blog.domain.User;
import com.hb.blog.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void userSave() throws Exception {
        User user = User.builder()
                .userName("test1")
                // .password("1234")
                .password(encoder.encode("1234"))
                .emailId("test@naver.com")
                .role(Role.USER)
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isEqualTo(user);
    }

}
