package com.sparta.boardhanghae.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;

    public Post(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
