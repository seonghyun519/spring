package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    List<Reply> findAllByBoardId(Long id);
    Optional<Reply> findByIdAndUserId(Long id, Long clientId);

    List<Reply> findAllByBoardIdOrderByModifiedAtDesc(Long id);

}
