package com.hb.blog.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.hb.blog.domain.Role;
import com.hb.blog.domain.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userSave() throws Exception {
        User user = User.builder()
                .userName("test1")
                .password("1234")
                .emailId("test@naver.com")
                .role(Role.USER)
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isEqualTo(user);
    }
}
