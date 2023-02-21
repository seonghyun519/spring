package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Reply;
import com.sparta.hanghaeboard.entity.ReplyLike;
import com.sparta.hanghaeboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByReplyIdAndUserId(Long id, Long userId);
    void deleteReplyLikeByUserId(Long UserId);
}