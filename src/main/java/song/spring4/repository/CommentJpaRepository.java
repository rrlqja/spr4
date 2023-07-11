package song.spring4.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.entity.Comment;

import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"board", "writer"})
    Optional<Comment> findEntityGraphById(Long id);

}
