package com.hb.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.blog.domain.RefreshToken;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByTokenKey(String tokenKey);

    boolean existsByTokenKey(String emailId);

    @Transactional
    void deleteByTokenKey(String emailId);
}
