package com.hb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

    public Page<BoardDto> getBoardList(String title, Pageable pageable) {
        // List<Board> boardEntites = boardRepository.findAllFetchJoin();
        // List<BoardDto> boardList = new ArrayList<>();
        // for (Board entity : boardEntites) {
        // BoardDto board = new BoardDto(entity);
        // boardList.add(board);
        // }

        // return boardList;

        Page<Board> boardEntites = findByTitleContaining(title, pageable);
        Page<BoardDto> boardList = boardEntites.map(m -> new BoardDto(m));
        return boardList;
    }

    public BoardDto getBoard(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("?"));

        return new BoardDto(entity);
    }

    public BoardDto insertBoard(BoardDto boardDto, User user) {
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

    public int updateView(Long id) {

        return boardRepository.updateView(id);
    }

    public Page<Board> findByTitleContaining(String title, Pageable pageable) {

        return boardRepository.findByTitleContaining(title, pageable);
    }
}
