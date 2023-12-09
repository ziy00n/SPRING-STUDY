package com.example.study.Repository;

import com.example.study.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // comment의 board_id 가 메소드 인자로 들어온 @Param(boardId)인 :boardId 인 것!
    // 원하는 게시판(board_id)에서 부모 댓글(parent=0) 인 Comment들(List<Comment>)을 조회해오는 함수
    @Query("SELECT c FROM Comment c WHERE c.board.id= :boardId AND c.parent= 0")
    List<Comment> findRootComments(@Param("boardId") Long boardId);

    List<Comment> findByParent(Long parentId);

    // Parent 속성에서 부모 id 댓글이 적힌 자식 댓글 개수 구하기
    Long countByParent(Long commentId);
}
