package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Reply;
import lombok.*;

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


    public ReplyResponseDto(Reply reply){
        this.id = reply.getId();
        this.content = reply.getContent();
        this.username = reply.getUser().getUsername();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }

//    public static ReplyResponseDto test(Reply reply){
//        return ReplyResponseDto.builder()
//                .id(reply.getId())
//                .content(reply.getContent())
//                .username(reply.getUser().getUsername())
//                .createdAt(reply.getCreatedAt())
//                .modifiedAt(reply.getModifiedAt())
//                .build();
//    }
}