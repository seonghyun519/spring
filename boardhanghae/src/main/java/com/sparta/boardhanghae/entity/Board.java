package com.sparta.boardhanghae.entity;

import com.sparta.boardhanghae.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int likeCount;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardLike> boardLikes = new ArrayList<>();

    public Board(BoardRequestDto requestDTO, User user){
        logger.info("Board Entity 정상 실행1");
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.user = user;
        this.likeCount = 0;
    }
    public void update(BoardRequestDto boardRequestDTO){
        logger.info("Board Entity 정상 실행2/관리자 수정");
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.user = user;
    }
    public void update(BoardRequestDto boardRequestDTO, User user){
        logger.info("Board Entity 정상 실행2");
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.user = user;
    }
    public void like(int count) {
        this.likeCount = count;
    }
}
