package com.hb.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.blog.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    Optional<User> findByEmailId(String emailId);
}
