package com.example.study.Controller;

import com.example.study.Entity.Board;
import com.example.study.Service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
public class MyPageController {
    private final MyPageService myPageService;

    // 내가 좋아요한 게시글 목록
    @GetMapping("/likes/{userId}")
    public ResponseEntity<List<Board>> getLikeBoards(@PathVariable Long userId) {
        List<Board> myLikes = myPageService.getLikedBoards(userId);
        return new ResponseEntity<>(myLikes, HttpStatus.OK);
    }

}
