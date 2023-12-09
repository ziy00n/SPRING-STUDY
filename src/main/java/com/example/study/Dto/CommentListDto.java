package com.example.study.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// 댓글, 대댓글 목록 확인 Dto
public class CommentListDto {
    private String content;
    private Long userId;
    private Long parent;

    private Long depth;
    private Boolean isDeleted;
    private List<CommentListDto> children;
}
