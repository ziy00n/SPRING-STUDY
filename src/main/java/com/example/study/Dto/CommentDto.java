package com.example.study.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 댓글, 대댓글 작성 시
public class CommentDto {
    private String content;
    private Long userId;
    private Long boardId;
    private Long parent; // 이 댓글이 대댓글(자식댓글이면) parent속성에 부모댓글 id 담김, 부모댓글이면 0
}
