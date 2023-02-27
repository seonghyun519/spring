package com.sparta.boardhanghae.repository;


import com.sparta.boardhanghae.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
