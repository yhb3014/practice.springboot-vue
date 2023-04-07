package com.hb.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.domain.User;
import com.hb.blog.dto.BoardDto;
import com.hb.blog.dto.ResponseDto;
import com.hb.blog.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 글 목록
     * 
     * TODO
     * 1. 페이징
     * 
     * @return
     */
    @GetMapping("/list")
    public ResponseDto<List<BoardDto>> getBoardList() {
        List<BoardDto> boardList = new ArrayList<>();

        return new ResponseDto<List<BoardDto>>(HttpStatus.OK.value(), boardList);
    }

    /**
     * 상세보기
     * 
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResponseDto<BoardDto> getBoard(@PathVariable Long id) {
        BoardDto board = boardService.getBoard(id);

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(), board);
    }

    /**
     * 글 쓰기
     * 
     * @param boardDto
     * @param user
     * @return
     */
    @PostMapping("/doPost")
    public ResponseDto<BoardDto> doPost(@RequestBody BoardDto boardDto, @AuthenticationPrincipal User user) {
        BoardDto board = boardService.doPost(boardDto, user);

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(), board);
    }

    /**
     * 글 수정
     * 
     * @param boardDto
     * @return
     */
    @PatchMapping("/update")
    public ResponseDto<BoardDto> updateBoard(@RequestBody BoardDto boardDto) {
        BoardDto board = boardService.updateBoard(boardDto);

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(), board);
    }

    /**
     * 글 삭제
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<Long> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);

        return new ResponseDto<Long>(HttpStatus.OK.value(), id);
    }

    /*
     * TODO
     * 1. 댓글 작성/수정/삭제
     */
}