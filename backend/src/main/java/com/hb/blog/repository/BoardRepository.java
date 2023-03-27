package com.hb.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.blog.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
