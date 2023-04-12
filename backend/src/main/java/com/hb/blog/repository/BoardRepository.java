package com.hb.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hb.blog.domain.Board;

import jakarta.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAll();

    @Query("select b from Board b join fetch b.user")
    List<Board> findAllFetchJoin();

    Page<Board> findByTitleContaining(String title, Pageable pageable);

    Optional<Board> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Board b set b.count = b.count + 1 where b.id = :id")
    int updateView(Long id);
}
