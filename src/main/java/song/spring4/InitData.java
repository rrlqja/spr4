package song.spring4;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.userrole.consts.Role;
import song.spring4.domain.board.entity.Board;
import song.spring4.domain.comment.entity.Comment;
import song.spring4.domain.user.entity.User;
import song.spring4.domain.file.entity.FileEntity;
import song.spring4.domain.board.repository.BoardJpaRepository;
import song.spring4.domain.comment.repository.CommentJpaRepository;
import song.spring4.domain.file.repository.FileEntityJpaRepository;
import song.spring4.domain.user.repository.UserJpaRepository;
import song.spring4.domain.file.service.FileEntityService;
import song.spring4.domain.userrole.service.UserRoleService;

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
        private final FileEntityService fileEntityService;
        private final UserJpaRepository userRepository;
        private final BoardJpaRepository boardRepository;
        private final CommentJpaRepository commentRepository;
        private final FileEntityJpaRepository fileEntityRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public void init1() {
            User userA = User.of("a", passwordEncoder.encode("a"), "nameA", "dkclasltmf22@naver.com");
            User saveUserA = userRepository.save(userA);

            User userB = User.of("b", passwordEncoder.encode("b"), "nameB", "");
            User saveUserB = userRepository.save(userB);

            userRoleService.grantRole(saveUserA.getId(), Role.ROLE_USER.name());
            userRoleService.grantRole(saveUserA.getId(), Role.ROLE_ADMIN.name());

            for (int i = 0; i < 30; i++) {
                Board board = Board.of(userA, "test title " + (i + 1), "test content" + (i + 1));
                Board saveBoard = boardRepository.save(board);
                if (i == 29) {
                    FileEntity spring = FileEntity.of("spring.png", "spring.png");
                    spring.setBoard(board);
                    fileEntityRepository.save(spring);
                    FileEntity security = FileEntity.of("security.png", "security.png");
                    security.setBoard(board);
                    fileEntityRepository.save(security);
                    board.updateBoard("title", "<p>board</p><br> <img src=/file/downloadFile/spring.png> <img src=/file/downloadFile/security.png>");
                }

                if (i == 0) {
                    Comment comment1 = Comment.of(saveUserA, saveBoard, null, "comment" + (i + 1));
                    Comment saveComment1 = commentRepository.save(comment1);

                    for (int j = 0; j < 3; j++) {
                        Comment reply = Comment.of(saveUserA, saveBoard, saveComment1, "comment" + (i + 1));
                        commentRepository.save(reply);
                    }

                    Comment comment2 = Comment.of(saveUserA, saveBoard, null, "comment" + (i + 1));
                    commentRepository.save(comment2);
                }
            }

        }
    }
}
