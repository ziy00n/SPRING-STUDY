package com.example.study.Service;

import com.example.study.Dto.LikesDto;
import com.example.study.Entity.Board;
import com.example.study.Entity.Likes;
import com.example.study.Entity.User;
import com.example.study.Repository.BoardRepository;
import com.example.study.Repository.LikesRepository;
import com.example.study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 좋아요 등록
    public void createLike(LikesDto likeDto) {
        User user = userRepository.findById(likeDto.getUser_id()).orElseThrow(NullPointerException::new);
        Board board = boardRepository.findById(likeDto.getBoard_id()).orElseThrow(NullPointerException::new);

        if (likesRepository.findByUserAndBoard(user, board).isEmpty()) {
            Likes newLike = new Likes();
            newLike.setBoard(board);
            newLike.setUser(user);
            likesRepository.save(newLike);
        }
    }

    // 좋아요 취소
    public void deleteLike(LikesDto likeDto) {
        User user = userRepository.findById(likeDto.getUser_id()).orElseThrow(NullPointerException::new);
        Board board = boardRepository.findById(likeDto.getBoard_id()).orElseThrow(NullPointerException::new);

        if (likesRepository.findByUserAndBoard(user, board).isPresent()) {
            likesRepository.deleteByUserAndBoard(user, board);
        }
    }
}
