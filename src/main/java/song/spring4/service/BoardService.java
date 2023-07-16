package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.*;
import song.spring4.entity.Board;
import song.spring4.entity.User;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.UserJpaRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public Long saveBoard(Long userId, RequestBoardDto requestBoardDto) {
        User user = getUserById(userId);

        Board board = requestBoardDto.toEntity();
        board.setWriter(user);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    @Transactional
    public ResponseBoardDto findBoardById(Long boardId) {
        Board findBoard = getBoardById(boardId);
        findBoard.setViews((findBoard.getViews() + 1));

        ResponseBoardDto responseBoardDto = new ResponseBoardDto(findBoard);

        return responseBoardDto;
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

    public Page<BoardListDto> findBoardByContent(String content, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAllByContentLike(content, pageable);

        return boardPage.map(BoardListDto::new);
    }

    @Transactional
    public Long editBoard(Long boardId, EditBoardDto editBoardDto) {
        Board findBoard = getBoardById(boardId);

        findBoard.setTitle(editBoardDto.getTitle());
        findBoard.setContent(editBoardDto.getContent());

        return findBoard.getId();
    }

    @Transactional
    public Long updateBoardTitle(Long boardId, String title) {
        Board findBoard = getBoardById(boardId);
        findBoard.setTitle(title);

        return findBoard.getId();
    }

    @Transactional
    public Integer bulkUpdateBoardTitle(String title, String updateTitle) {
        Integer result = boardRepository.updateBoardTitle(title, updateTitle);

        return result;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
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
}
