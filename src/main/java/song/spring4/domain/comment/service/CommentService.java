package song.spring4.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.comment.dto.EditCommentDto;
import song.spring4.domain.comment.dto.RequestCommentDto;
import song.spring4.domain.board.Board;
import song.spring4.domain.comment.Comment;
import song.spring4.domain.user.User;
import song.spring4.exception.IllegalRequestArgumentException;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.exception.notfoundexception.CommentNotFoundException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.comment.repository.CommentJpaRepository;
import song.spring4.domain.user.repository.UserJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentJpaRepository commentRepository;
    private final BoardJpaRepository boardRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public Long saveComment(Long userId, RequestCommentDto requestCommentDto) {
        User user = getUserById(userId);
        Board board = getBoardById(requestCommentDto.getBoardId());
        Comment parent = getParent(requestCommentDto.getParentId());

        Comment comment = Comment.of(user, board, parent, requestCommentDto.getContent());
        Comment saveComment = commentRepository.save(comment);

        return saveComment.getBoard().getId();
    }

    @Transactional
    public Comment findCommentById(Long id) {

        return getCommentById(id);
    }

    @Transactional
    public Long editComment(Long userId, EditCommentDto editCommentDto) {
        Comment findComment = getCommentById(editCommentDto.getId());
        if (!userId.equals(findComment.getUser().getId())) {
            throw new IllegalRequestArgumentException("잘못된 요청입니다.");
        }
        findComment.updateContent(editCommentDto.getContent());

        Comment editComment = commentRepository.save(findComment);

        return editComment.getBoard().getId();
    }

    @Transactional
    public Long deleteComment(Long id) {
        Comment comment = getCommentById(id);

        comment.delete();
        Comment saveComment = commentRepository.save(comment);
        return saveComment.getId();
    }

    private User getUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private Board getBoardById(Long boardId) {
        Optional<Board> findBoard = boardRepository.findEntityGraphById(boardId);
        if (findBoard.isEmpty()) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
        }
        return findBoard.get();
    }

    private Comment getParent(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return getCommentById(parentId);
    }

    private Comment getCommentById(Long id) {
        Optional<Comment> findComment = commentRepository.findEntityGraphById(id);
        if (findComment.isEmpty()) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }
        return findComment.get();
    }

}
