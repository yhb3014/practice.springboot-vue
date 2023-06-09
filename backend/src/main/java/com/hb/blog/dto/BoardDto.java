package com.hb.blog.dto;

import java.time.format.DateTimeFormatter;

import com.hb.blog.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long id;

    private String title;

    private String content;

    private int count;

    private String createDate;

    private String userName;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.count = board.getCount();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        this.userName = board.getUser().getUserName();
    }

}
