package com.hb.blog.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hb.blog.domain.Board;
import com.hb.blog.domain.Role;
import com.hb.blog.domain.User;
import com.hb.blog.dto.BoardDto;
import com.hb.blog.repository.BoardRepository;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    void testGetBoardList() {
        List<Board> boardList = new ArrayList<>();
        Board board1 = Board.builder()
                .title("test1")
                .content("test1")
                .count(1)
                .build();
        board1.setCreateDate(LocalDateTime.now());
        boardList.add(board1);
        when(boardRepository.findAll()).thenReturn(boardList);

        List<BoardDto> result = boardService.getBoardList();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testGetBoard() {
        Board board = Board.builder()
                .title("test1")
                .content("test1")
                .count(1)
                .build();
        board.setCreateDate(LocalDateTime.now());
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        BoardDto result = boardService.getBoard(1L);

        assertThat(result.getTitle()).isEqualTo(board.getTitle());
    }

    @Test
    void testDoPost() {
        User user = User.builder()
                .userName("test1")
                .password("1234")
                .emailId("test@naver.com")
                .role(Role.USER)
                .build();

        BoardDto boardDto = BoardDto.builder()
                .title("test1")
                .content("test1")
                .count(1)
                .build();
        boardService.insertBoard(boardDto, user);

        verify(boardRepository, times(1)).save(any());
    }

    @Test
    void testDeleteBoard() {
        Board board = Board.builder()
                .id(1L)
                .title("test1")
                .content("test1")
                .count(1)
                .build();

        boardService.deleteBoard(board.getId());

        verify(boardRepository, times(1)).delete(any());
    }

}
