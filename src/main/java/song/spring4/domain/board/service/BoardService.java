package song.spring4.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.board.dto.RequestBoardDto;
import song.spring4.domain.board.dto.ResponseBoardDto;
import song.spring4.domain.board.dto.BoardListDto;
import song.spring4.domain.board.dto.EditBoardDto;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.comment.repository.CommentJpaRepository;
import song.spring4.domain.user.entity.User;
import song.spring4.exception.invalid.exceptions.InvalidUserException;
import song.spring4.exception.notfound.exceptions.BoardNotFoundException;
import song.spring4.exception.notfound.exceptions.UserNotFoundException;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.user.repository.UserJpaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardRepository;
    private final UserJpaRepository userRepository;
    private final CommentJpaRepository commentRepository;

    @Transactional
    public Long saveBoard(Long userId, RequestBoardDto requestBoardDto) {
        User user = getUserById(userId);

        Board board = Board.of(user, requestBoardDto.getTitle(), requestBoardDto.getContent());
        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    @Transactional
    public ResponseBoardDto findBoardById(Long boardId) {
        Board board = getBoardById(boardId);
        board.increaseViews();

        List commentList = commentRepository.findByBoardId(board.getId());

        return new ResponseBoardDto(board, commentList);
    }

    @Transactional
    public Page<BoardListDto> findBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(BoardListDto::new);
    }

    @Transactional
    public Page<BoardListDto> findBoardListByUsername(String username, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAllByUsernameLike(username, pageable);

        return boardPage.map(BoardListDto::new);
    }

    @Transactional
    public Page<BoardListDto> findBoardListByTitle(String title, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAllByTitleLike(title, pageable);

        return boardPage.map(BoardListDto::new);
    }

    @Transactional
    public Page<BoardListDto> findBoardByContent(String content, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAllByContentLike(content, pageable);

        return boardPage.map(BoardListDto::new);
    }

    @Transactional
    public Long editBoard(Long boardId, EditBoardDto editBoardDto, Long userId) {
        Board board = getBoardById(boardId);
        validateWriter(userId, board);

        board.editBoard(editBoardDto.getTitle(), editBoardDto.getContent());

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);
        validateWriter(userId, board);
        boardRepository.deleteById(boardId);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    private void validateWriter(Long userId, Board board) {
        if (!userId.equals(board.getUser().getId())) {
            throw new InvalidUserException();
        }
    }
}
