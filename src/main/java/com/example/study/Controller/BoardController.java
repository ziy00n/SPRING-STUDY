package com.example.study.Controller;

import com.example.study.Dto.BoardDto;
import com.example.study.Entity.Board;
import com.example.study.Service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        Optional<Board> board = boardService.getBoardById(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
    * map 메서드 > Optional에서 사용
    * map 메서드 : Optional사용했는데, 존재할 때만 map 메소드사용됌.
    * Optional<board>가 존재하면 -> map메소드 실행 . ok 빌드.
    * 없으면( orElseGet()메소드 실행 ) notFound : 404 페이지 빌드
    * */

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto) {
        Board newBoard = boardService.createBoard(boardDto);
        return ResponseEntity.ok(newBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        // boardDto : 수정될 제목, 내용 담겨짐
        // id : 수정할 보드의 id
        Board updateBoard = boardService.updateBoard(id, boardDto);
        return updateBoard != null ? ResponseEntity.ok(updateBoard) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
