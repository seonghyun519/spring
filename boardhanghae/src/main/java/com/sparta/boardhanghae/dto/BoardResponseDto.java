package com.sparta.boardhanghae.dto;

import com.sparta.boardhanghae.entity.Board;
import com.sparta.boardhanghae.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<ReplyResponseDto> replyList = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.username = board.getUser().getUsername();

        for (Reply reply : board.getReplyList()){
            this.replyList.add(new ReplyResponseDto(reply));
        }
    }
    public BoardResponseDto(Board board, List<ReplyResponseDto> reply) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.username = board.getUser().getUsername();

        this.replyList = reply;
    }

}
