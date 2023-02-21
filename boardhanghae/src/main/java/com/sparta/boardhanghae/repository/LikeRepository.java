package com.sparta.boardhanghae.repository;


import com.sparta.boardhanghae.entity.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByReplyIdAndUserId(Long id, Long userId);
    void deleteReplyLikeByUserId(Long UserId);
}