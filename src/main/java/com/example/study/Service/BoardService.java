package com.example.study.Service;

import com.example.study.Dto.BoardDto;
import com.example.study.Entity.Board;
import com.example.study.Repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시글 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // Optional 사용이유 : 특정아이디에 맞는 게시글이없을 수도 있음
    // 안의 값이 존재하지 않을수도 있을 때 쓰는 컨테이너!
    // 옵셔널 : 확장성 많음
    // 만약에 값 없으면 다른 값 지정 바로 할 수도 있음.

    // 특정 ID의 게시글 조회
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    // 새로운 게시글 작성
    public Board createBoard(BoardDto boardDto) {
        Board newBoard = new Board();
        newBoard.setTitle(boardDto.getTitle());
        newBoard.setContent(boardDto.getContent());

        return boardRepository.save(newBoard);
    }

    // 게시글 업데이트
    public Board updateBoard(Long id, BoardDto boardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            Board existingBoard =  optionalBoard.get();
            existingBoard.setTitle(boardDto.getTitle());
            existingBoard.setContent(boardDto.getContent());

            return boardRepository.save(existingBoard);
        }

        return null;
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

}