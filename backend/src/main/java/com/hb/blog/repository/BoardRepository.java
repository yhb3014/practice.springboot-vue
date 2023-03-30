package com.hb.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.blog.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAll();

    Optional<Board> findById(Long id);
}
