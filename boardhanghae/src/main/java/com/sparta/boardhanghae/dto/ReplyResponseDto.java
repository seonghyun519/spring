package com.sparta.boardhanghae.dto;

import com.sparta.boardhanghae.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class ReplyResponseDto {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int like;

    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
        this.username = reply.getUser().getUsername();
        this.like = reply.getLikeCount();
    }

//    public ReplyResponseDto test(Reply reply){
//        return ReplyResponseDto.builder()
//                .id(reply.getId())
//                .content(reply.getContent())
//                .username(reply.getUser().getUsername())
//                .createdAt(reply.getCreatedAt())
//                .modifiedAt(reply.getModifiedAt())
//                .build();
//    }
}