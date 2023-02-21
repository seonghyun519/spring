package com.sparta.boardhanghae.repository;

import com.sparta.boardhanghae.entity.BoardLike;
import com.sparta.boardhanghae.entity.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardIdAndUserId(Long id, Long userId);
    void deleteBoardLikeByUserId(Long UserId);

}
