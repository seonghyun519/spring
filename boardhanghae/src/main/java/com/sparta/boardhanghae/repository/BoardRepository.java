package com.sparta.boardhanghae.repository;


import com.sparta.boardhanghae.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc(); //findAll/ByOrderBy/ModifiedAt순으로 정렬할거고/Desc 내림차순 정렬
    Optional<Board> findByIdAndUserId(Long id, Long clientId);

//    List<Board> findByIdAndPwd(String Id, String Pwd);
}
