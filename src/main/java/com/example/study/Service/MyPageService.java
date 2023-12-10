package com.example.study.Service;

import com.example.study.Entity.Board;
import com.example.study.Entity.Likes;
import com.example.study.Entity.User;
import com.example.study.Repository.LikesRepository;
import com.example.study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final LikesRepository likeRepository;

    // 좋아요 한 게시판 글 목록 보기
    public List<Board> getLikedBoards(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        List<Likes> likedList = likeRepository.findByUser(user); // 내가 한 좋아요 List

        return likedList.stream().map(Likes::getBoard).collect(Collectors.toList()); // 좋아요List에서 Getter getBoard()를 통해 List<Board> 의 형태로 변환
    }
}
