package song.spring4.domain.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.spring4.domain.comment.dto.RequestCommentDto;
import song.spring4.domain.comment.entity.Comment;
import song.spring4.domain.comment.repository.CommentJpaRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentJpaRepository commentRepository;

    @Test
    void save1() {
        RequestCommentDto requestCommentDto = new RequestCommentDto();
        requestCommentDto.setParentId(null);
        requestCommentDto.setBoardId(1L);
        requestCommentDto.setContent("comment1");

        Long id = commentService.saveComment(1L, requestCommentDto);

        Comment findComment = commentRepository.findById(id).get();

        assertThat(findComment.getBoard().getTitle()).isEqualTo("title1");
    }

    @Test
    void find1() {
        assertThat(commentService.findCommentById(1L).getContent()).isEqualTo("comment1");
    }

    @Test
    void delete1() {
//        Long id = commentService.deleteComment(1L);
//        assertThat(commentRepository.findById(id).get().getWriter()).isNull();
    }
}