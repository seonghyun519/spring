package com.sparta.boardhanghae.entity;

import com.sparta.boardhanghae.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReplyLike> ReplyLikes = new ArrayList<>();

    public Reply(User user, Board board, String content) {
        this.user = user;
        this.board = board;
        this.content = content;
        this.likeCount = 0;
    }

    public void update(ReplyRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void like(int count) {
        this.likeCount = count;
    }
}