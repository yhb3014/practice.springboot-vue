package com.hb.blog.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hb.blog.domain.Board;
import com.hb.blog.domain.User;
import com.hb.blog.dto.BoardDto;
import com.hb.blog.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> getBoardList() {
        List<Board> boardEntites = boardRepository.findAll();
        List<BoardDto> boardList = new ArrayList<>();
        for (Board entity : boardEntites) {
            BoardDto board = BoardDto.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .user(entity.getUser())
                    .createDate(entity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")))
                    .build();
            boardList.add(board);
        }

        return boardList;
    }

    public BoardDto getBoard(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("?"));

        return new BoardDto(entity);
    }

    public BoardDto doPost(BoardDto boardDto, User user) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .count(0)
                .user(user)
                .build();

        return new BoardDto(boardRepository.save(board));
    }

    public BoardDto updateBoard(BoardDto boardDto) {
        Board entity = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new RuntimeException("?"));
        entity.setTitle(boardDto.getTitle());
        entity.setContent(boardDto.getContent());

        return new BoardDto(boardRepository.save(entity));
    }

    public void deleteBoard(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("?"));
        boardRepository.delete(entity);
    }
}
