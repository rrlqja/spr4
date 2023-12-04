package song.spring4;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.dto.UploadFileDto;
import song.spring4.domain.board.Board;
import song.spring4.domain.comment.Comment;
import song.spring4.domain.user.User;
import song.spring4.entity.role.RoleName;
import song.spring4.repository.BoardJpaRepository;
import song.spring4.repository.CommentJpaRepository;
import song.spring4.repository.UserJpaRepository;
import song.spring4.service.FileEntityService;
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
        private final FileEntityService fileEntityService;
        private final UserJpaRepository userRepository;
        private final BoardJpaRepository boardRepository;
        private final CommentJpaRepository commentRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public void init1() {
            User userA = User.builder()
                    .username("a")
                    .password(passwordEncoder.encode("a"))
                    .name("nameA")
                    .email("dkclasltmf22@naver.com")
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();
            User saveUserA = userRepository.save(userA);

            User userB = User.builder()
                    .username("b")
                    .password(passwordEncoder.encode("a"))
                    .name("nameB")
//                    .email("dkclasltmf@naver.com")
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();

            User saveUserB = userRepository.save(userB);

            userRoleService.grantRole(saveUserA.getId(), RoleName.ROLE_USER);
            userRoleService.grantRole(saveUserA.getId(), RoleName.ROLE_ADMIN);

            for (int i = 0; i < 30; i++) {
                Board board = Board.builder()
                        .title("title" + (i + 1))
                        .content("content" + (i + 1))
                        .build();
                board.setWriter(userA);
                Board saveBoard = boardRepository.save(board);
                if (i == 29) {
                    UploadFileDto uploadFileDto = new UploadFileDto("spring.png", "spring.png");
                    fileEntityService.saveFileEntity(uploadFileDto);
                    board.updateBoard("title", "<p>board</p><br> <img src=/file/downloadFile/spring.png>");
                    fileEntityService.attachFileEntityToBoard(uploadFileDto.getFileName(), saveBoard.getId());
                }

                if (i == 0) {
                    Comment comment1 = new Comment();
                    comment1.setBoard(saveBoard);
                    comment1.setWriter(saveUserA);
                    comment1.setContent("comment" + (i + 1));
                    Comment saveComment1 = commentRepository.save(comment1);

                    for (int j = 0; j < 3; j++) {
                        Comment reply = new Comment();
                        reply.setBoard(saveBoard);
                        reply.setWriter(saveUserA);
                        reply.setParent(saveComment1);
                        reply.setContent("reply" + (j + 1));
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
