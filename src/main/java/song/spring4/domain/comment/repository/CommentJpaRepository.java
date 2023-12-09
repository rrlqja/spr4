package song.spring4.domain.comment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"board", "user"})
    Optional<Comment> findById(Long id);

    @EntityGraph(attributePaths = "user")
    List findByBoardId(Long boardId);
}
