package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.ReplyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public Reply(User user, Board board, String content){
        this.user = user;
        this.board = board;
        this.content = content;
    }

    public  void update(ReplyRequestDto requestDto){
        this.content = requestDto.getContent();
    }
}