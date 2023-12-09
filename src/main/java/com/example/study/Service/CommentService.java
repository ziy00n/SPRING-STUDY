package com.example.study.Service;

import com.example.study.Dto.CommentDto;
import com.example.study.Dto.CommentListDto;
import com.example.study.Dto.CommentUpdateDto;
import com.example.study.Entity.Board;
import com.example.study.Entity.Comment;
import com.example.study.Entity.User;
import com.example.study.Repository.BoardRepository;
import com.example.study.Repository.CommentRepository;
import com.example.study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록
    public void postComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(NullPointerException::new);
        Board board = boardRepository.findById(commentDto.getBoardId()).orElseThrow(NullPointerException::new);

        Comment newComment = new Comment();
        newComment.setContent(commentDto.getContent());
        newComment.setUser(user);
        newComment.setBoard(board);
        newComment.setDepth(0L);// Long타입이기 때문에 0 -> 0L 로 입력하기 // 부모댓글이므로 depth 0
        newComment.setParent(0L);// 부모댓글이므로 0L
        newComment.setIsDeleated(false);

        commentRepository.save(newComment);
    }

    public void postReplyComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(NullPointerException::new);
        Board board = boardRepository.findById(commentDto.getBoardId()).orElseThrow(NullPointerException::new);

        Comment newReplyComment = new Comment();
        newReplyComment.setContent(commentDto.getContent());
        newReplyComment.setUser(user);
        newReplyComment.setBoard(board);
        newReplyComment.setDepth(1L);// 대댓글이므로 1
        newReplyComment.setParent(commentDto.getParent());// 자식댓글이므로 부모의 pk(id)
        newReplyComment.setIsDeleated(false);

        commentRepository.save(newReplyComment);
    }

    public List<CommentListDto> getCommentHierarchy(Long boardId) {
        // 부모 댓글 리스트가져오기
        List<Comment> rootComments = commentRepository.findRootComments(boardId);

        // 받아온 rootComment[Comment엔티티타입] 인 것을 CommentListDto 로 변경해주기
        List<CommentListDto> rootCommentsWithChildDto = new ArrayList<>();
        for (Comment rootComment : rootComments) {
            CommentListDto rootCommentDto = convertToDto(rootComment);

            rootCommentDto.setChildren(getChildrenCommentsDTO(rootComment.getId()));
            rootCommentsWithChildDto.add(rootCommentDto);
        }
        return rootCommentsWithChildDto;
    }

    private List<CommentListDto> getChildrenCommentsDTO(Long parentId) {
        // 특정 부모 댓글에 속한 자식 댓글 가져오기
        List<Comment> children = commentRepository.findByParent(parentId);

        // 이 부분에 의해 댓글의 계층을 계속 생성하고 가져오는 것.
        List<CommentListDto> childrenDto = new ArrayList<>();
        for (Comment child : children) {
            CommentListDto childDto = convertToDto(child);

            childDto.setChildren(getChildrenCommentsDTO(child.getId()));
            childrenDto.add(childDto);
        }
        return childrenDto;

    }

    // Comment -> CommentListDto 로 변경하는 함수
    private CommentListDto convertToDto(Comment comment) {
        CommentListDto commentDto = new CommentListDto();
        commentDto.setContent(comment.getContent());
        commentDto.setDepth(comment.getDepth());
        commentDto.setParent(comment.getParent());
        commentDto.setIsDeleted(comment.getIsDeleated());
        commentDto.setUserId(comment.getUser().getUserId());

        return commentDto;
    }

    public void updateComment(Long comment_id, CommentUpdateDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        if (optionalComment.isPresent()) {
            Comment existingComments = optionalComment.get();
            // 내용만 변경
            existingComments.setContent(commentDto.getContent());
            commentRepository.save(existingComments);
        }
    }

    public void deleteComment(Long comment_id) {
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        // 부모댓글에 자식댓글이 달렸는지 개수 확인
        Long childCount = commentRepository.countByParent(comment_id);

        if (optionalComment.isPresent()) {
            // 자식댓글 -> 바로 삭제
            if (optionalComment.get().getDepth() == 1) {
                commentRepository.deleteById(comment_id);
            } else { // 부모댓글

                if (childCount == 0L) { // 자식없는 부모댓글 -> 바로 삭제
                    commentRepository.deleteById(comment_id);
                } else { // 자식있는 부모댓글 -> 부모의 isDeleted 속성을 1(true)로 변경
                    Comment existingComment = optionalComment.get();
                    existingComment.setIsDeleated(true);
                    commentRepository.save(existingComment);
                }
            }
        }
    }
}
