package com.hb.blog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hb.blog.config.auth.PrincipalDetails;
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
    public ResponseDto<Page<BoardDto>> getBoardList(@RequestParam(defaultValue = "") String title,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return new ResponseDto<Page<BoardDto>>(HttpStatus.OK.value(), boardService.getBoardList(title, pageable));
    }

    /**
     * 상세보기
     * 
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResponseDto<BoardDto> getBoardDetail(@PathVariable Long id) {
        BoardDto boardDto = boardService.getBoard(id);
        boardService.updateView(id);

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(), boardDto);
    }

    /**
     * 글 쓰기
     * 
     * @param boardDto
     * @param user
     * @return
     */
    @PostMapping("/doPost")
    public ResponseDto<BoardDto> insertBoard(@RequestBody BoardDto boardDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(),
                boardService.insertBoard(boardDto, principalDetails.getUser()));
    }

    /**
     * 글 수정
     * 
     * @param boardDto
     * @return
     */
    @PatchMapping("/update")
    public ResponseDto<BoardDto> updateBoard(@RequestBody BoardDto boardDto) {

        return new ResponseDto<BoardDto>(HttpStatus.OK.value(), boardService.updateBoard(boardDto));
    }

    /**
     * 글 삭제
     * 
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseDto<Long> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);

        return new ResponseDto<Long>(HttpStatus.OK.value(), id);
    }

    /*
     * TODO
     * 1. 댓글 작성/수정/삭제
     */
}