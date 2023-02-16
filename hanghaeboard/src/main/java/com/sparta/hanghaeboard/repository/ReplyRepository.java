package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBoardId(Long id);
}
