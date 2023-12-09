package com.example.study.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment { // 댓글,대댓글 기능을 ( 부모-자식 계층 ) Comment 클래스 하나로 나타냄
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    private String content;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    private Long depth; // 부모 댓글 0, 자식 댓글 1

    private Long parent; // 부모댓글이면 0, 자식댓글이면 부모의 id 를 넣어줌.

    private Boolean isDeleated; // 부모댓글이 삭제 될때, 자식댓글이 있을 시 부모댓글만 삭제됨을 표시하기 위해 쓰이는 속성
}
