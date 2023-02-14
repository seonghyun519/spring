package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor //파라미터 없는 기본생성자 생성
public class Board extends Timestamped{
    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Board(BoardRequestDto requestDTO, User user){ // dddddddddddddddddddddddddddddddddddddddddd
        logger.info("Board Entity 정상 실행1");
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.user = user;
    }
    public void update(BoardRequestDto boardRequestDTO){//, User user          dddddddddddddddddddddddddddddddd
        logger.info("Board Entity 정상 실행2");
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.user = user;
    }
}
