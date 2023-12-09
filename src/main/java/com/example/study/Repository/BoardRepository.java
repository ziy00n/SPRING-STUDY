package com.example.study.Repository;

import com.example.study.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    List<Board> findByTitle(String title);
//    List<Board> findByTitleAndContent(String title, String content);
//
//    @Query("SELECT b FROM Board b WHERE b.title = :title")
//    List<Board> findByTitle(@Param("title") String title);

}
// JPA 확장
/*
* JpaRepository : CRUD 기능 기본 구현됨
* 여기서 제공하는 메서드 이름 규칙이 있음
* 그걸 사용하면 내가 직접구현하지 않아도 됨!
* */
