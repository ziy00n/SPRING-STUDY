package com.example.study.Repository;

import com.example.study.Entity.Board;
import com.example.study.Entity.Likes;
import com.example.study.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // delete일때 transaction문제가 생길 수 있어서,(update나 delete처럼 트랜잭션이 깨질수있는 것에 필요)
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Object> findByUserAndBoard(User user, Board board);

    void deleteByUserAndBoard(User user, Board board); // like_id로 삭제하는게 아니라 User, Board있는지 확인 후 삭제하는 것이라 그럼!

    // 마이페이지에서 추가
    List<Likes> findByUser(User user);
}
