package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.boarddto.BoardDto;
import song.spring4.dto.boarddto.SaveBoardDto;
import song.spring4.dto.boarddto.BoardListDto;
import song.spring4.dto.boarddto.EditBoardDto;
import song.spring4.domain.board.Board;
import song.spring4.domain.user.User;
import song.spring4.exception.notfoundexception.BoardNotFoundException;
import song.spring4.exception.notfoundexception.UserNotFoundException;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public Long saveBoard(Long userId, SaveBoardDto saveBoardDto) {
        User user = getUserById(userId);

        Board board = Board.of(user, saveBoardDto.getTitle(), saveBoardDto.getContent());
        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    @Transactional
    public BoardDto findBoardById(Long boardId) {
        Board findBoard = getBoardById(boardId);
        findBoard.increaseViews();

        Board saveBoard = boardRepository.save(findBoard);

        BoardDto boardDto = new BoardDto(saveBoard);

        return boardDto;
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

        findBoard.updateBoard(editBoardDto.getTitle(), editBoardDto.getContent());

        Board saveBoard = boardRepository.save(findBoard);

        return saveBoard.getId();
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId).ifPresent(board ->
                boardRepository.delete(board));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Board getBoardById(Long boardId) {
        return boardRepository.findEntityGraphById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
