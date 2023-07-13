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
import song.spring4.entity.Role;
import song.spring4.entity.User;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.CommentJpaRepository;
import song.spring4.repository.UserJpaRepository;
import song.spring4.service.RoleService;
import song.spring4.service.UserRoleService;

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

        private final UserRoleService userRoleService;
        private final UserJpaRepository userRepository;
        private final BoardJpaRepository boardRepository;
        private final CommentJpaRepository commentRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public void init1() {
            User userA = new User();
            userA.setUsername("a");
            userA.setPassword(passwordEncoder.encode("a"));
            userA.setName("a");
            userA.setEmail("dkclasltmf22@naver.com");
            userA.setEnabled(true);
            userA.setAccountNonExpired(true);
            userA.setAccountNonLocked(true);
            userA.setCredentialsNonExpired(true);
            User saveUserA = userRepository.save(userA);

            User userB = new User();
            userB.setUsername("b");
            userB.setPassword(passwordEncoder.encode("b"));
            userB.setName("b");
            userB.setEnabled(true);
            userB.setAccountNonExpired(true);
            userB.setAccountNonLocked(true);
            userB.setCredentialsNonExpired(true);
            User saveUserB = userRepository.save(userB);

            userRoleService.grantRole(saveUserA.getId(), RoleName.ROLE_ADMIN);

            for (int i = 0; i < 10; i++) {
                Board board = new Board();
                board.setWriter(saveUserA);
                board.setTitle("title" + i);
                board.setContent("content" + i);
                Board saveBoard = boardRepository.save(board);

                if (i == 0) {
                    Comment comment1 = new Comment();
                    comment1.setBoard(saveBoard);
                    comment1.setWriter(saveUserA);
                    comment1.setContent("comment" + i);
                    Comment saveComment1 = commentRepository.save(comment1);

                    for (int j = 0; j < 3; j++) {
                        Comment reply = new Comment();
                        reply.setBoard(saveBoard);
                        reply.setWriter(saveUserA);
                        reply.setParent(saveComment1);
                        reply.setContent("reply" + j);
                        commentRepository.save(reply);
                    }

                    Comment comment2 = new Comment();
                    comment2.setBoard(saveBoard);
                    comment2.setWriter(saveUserA);
                    comment2.setContent("comment" + (i + 1));
                    commentRepository.save(comment2);
                }
            }

        }
    }
}
