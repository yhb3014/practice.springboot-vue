package com.hb.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.dto.BoardDto;
import com.hb.blog.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public List<BoardDto> getBoardList() {

        List<BoardDto> boardList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            BoardDto board = BoardDto.builder()
                    .id(i)
                    .title("제목" + i)
                    .user("유저" + i)
                    .count(1)
                    .createDate("1111-11-11")
                    .build();
            boardList.add(board);
        }

        return boardList;
    }
}
