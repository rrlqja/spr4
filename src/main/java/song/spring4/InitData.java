package song.spring4;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.entity.Board;
import song.spring4.entity.Comment;
import song.spring4.entity.User;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.CommentJpaRepository;
import song.spring4.repository.UserJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void doInit() {
        initService.init1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final UserJpaRepository userRepository;
        private final BoardJpaRepository boardRepository;
        private final CommentJpaRepository commentRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public void init1() {
            User user = new User();
            user.setUsername("a");
            user.setPassword(passwordEncoder.encode("a"));
            user.setName("a");
            user.setEmail("dkclasltmf22@naver.com");
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            User saveUser = userRepository.save(user);

            for (int i = 0; i < 10; i++) {
                Board board = new Board();
                board.setWriter(saveUser);
                board.setTitle("title" + i);
                board.setContent("content" + i);
                Board saveBoard = boardRepository.save(board);

                if (i == 0) {
                    Comment comment1 = new Comment();
                    comment1.setBoard(saveBoard);
                    comment1.setWriter(saveUser);
                    comment1.setContent("content" + i);
                    commentRepository.save(comment1);

                    Comment comment2 = new Comment();
                    comment2.setBoard(saveBoard);
                    comment2.setWriter(saveUser);
                    comment2.setContent("content" + (i + 1));
                    commentRepository.save(comment2);
                }
            }
        }
    }
}
