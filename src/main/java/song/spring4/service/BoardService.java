package song.spring4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.RequestBoardDto;
import song.spring4.dto.ResponseBoardDto;
import song.spring4.dto.UpdateBoardDto;
import song.spring4.entity.Board;
import song.spring4.entity.User;
import song.spring4.exception.IllegalRequestArgumentException;
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
    public Long saveBoard(Long id, RequestBoardDto requestBoardDto) {
        User user = getUserById(id);

        Board board = requestBoardDto.toEntity();
        board.setWriter(user);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    @Transactional
    public ResponseBoardDto findBoardById(Long id) {
        Board findBoard = getBoardById(id);

        ResponseBoardDto responseBoardDto = new ResponseBoardDto(findBoard);

        return responseBoardDto;
    }

    @Transactional
    public Page<ResponseBoardDto> findBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(ResponseBoardDto::new);
    }

    @Transactional
    public Long updateBoard(Long id, UpdateBoardDto updateBoardDto) {
        Board findBoard = getBoardById(id);

        findBoard.setTitle(updateBoardDto.getTitle());
        findBoard.setContent(updateBoardDto.getContent());

        Board saveBoard = boardRepository.save(findBoard);

        return saveBoard.getId();
    }

    @Transactional
    public void deleteBoard(Long writerId, Long boardId) {
        Board findBoard = getBoardById(boardId);
        if (!findBoard.getWriter().getId().equals(writerId)) {
            throw new IllegalRequestArgumentException("작성자가 아닙니다.");
        }

        boardRepository.delete(findBoard);
    }

    private User getUserById(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private Board getBoardById(Long id) {
        Optional<Board> findBoard = boardRepository.findById(id);
        if (findBoard.isEmpty()) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다.");
        }
        return findBoard.get();
    }

}
