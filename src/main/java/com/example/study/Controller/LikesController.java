package com.example.study.Controller;

import com.example.study.Dto.LikesDto;
import com.example.study.Service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likeService;

    // 좋아요 등록
    @PostMapping("/")
    public ResponseEntity<String> insert(@RequestBody LikesDto likeDto) {
        likeService.createLike(likeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 등록 성공");
    }

    // 좋아요 취소
    @DeleteMapping("/") // 위에 Post메서드와 url같지만 다른 메소드라서 가능.
    public ResponseEntity<Void> delete(@RequestBody LikesDto likeDto) {
        likeService.deleteLike(likeDto);
        return ResponseEntity.noContent().build();
    }

}
