package song.spring4.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import song.spring4.dto.RequestCommentDto;
import song.spring4.entity.Comment;
import song.spring4.repository.CommentJpaRepository;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentJpaRepository commentRepository;

    @Test
    void save1() {
        RequestCommentDto requestCommentDto = new RequestCommentDto();
        requestCommentDto.setContent("comment1");
        requestCommentDto.setBoardId(1L);
        requestCommentDto.setParentId(null);

        Long id = commentService.saveComment(1L, requestCommentDto);

        Comment findComment = commentRepository.findEntityGraphById(id).get();

        Assertions.assertThat(findComment.getBoard().getTitle()).isEqualTo("title0");
    }

}