package com.hb.blog.service;

import org.springframework.stereotype.Service;

import com.hb.blog.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
}
