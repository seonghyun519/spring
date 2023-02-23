package com.sparta.boardhanghae.dto;

import com.sparta.boardhanghae.entity.Board;
import com.sparta.boardhanghae.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int like;
    private List<ReplyResponseDto> replyList = new ArrayList<>();

    //sort가 빠를까 for문이 빠를까
    public BoardResponseDto(Board board) { //전체 페이지
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.username = board.getUser().getUsername();
        this.like = board.getLikeCount();

        List<Reply>replies=board.getReplyList();
        replies.sort(Comparator.comparing(Reply::getModifiedAt).reversed());
        //reversed()역순 //디폴트가 오름차순으로 .reversed()로 역순 적용
        for (Reply reply : replies) {
            this.replyList.add(new ReplyResponseDto(reply));
        }
    }
    public BoardResponseDto(Board board, List<ReplyResponseDto> reply) { //상세 페이지
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.username = board.getUser().getUsername();
        this.like = board.getLikeCount();

        this.replyList = reply;
    }

}