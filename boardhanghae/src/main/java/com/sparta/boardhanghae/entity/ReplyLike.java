package com.sparta.hanghaeboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "REPLY_ID")
    Reply reply;

    public ReplyLike(User user, Reply reply) {
        this.user = user;
        this.reply = reply;
    }
}
