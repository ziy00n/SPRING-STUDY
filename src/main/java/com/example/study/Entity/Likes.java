package com.example.study.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likesId;

    // 좋아요 N : 게시판 1 다대일
    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name="board_id") // board테이블의 id를 Likes테이블에서의 컬럼명을 정할 때 JoinColumn
    private Board board;

    // 좋아요 N : 유저 1 다대일
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;
}