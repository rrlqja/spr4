package song.spring4.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.board.Board;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class BoardJpaRepositoryTest {

    @Autowired
    BoardJpaRepository boardRepository;
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void update1() {
        Board findBoard = boardRepository.findById(1L).get();
        findBoard.updateBoard("updateTitle", "updateContent"); // select

//        boardRepository.save(findBoard); // select -> update
        em.flush(); // update
        em.clear();

        Board board = boardRepository.findById(1L).get(); // select
        System.out.println("board.getTitle() = " + board.getTitle());
    }

}