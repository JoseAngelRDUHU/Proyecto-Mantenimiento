package repositories;

import domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.article.id = :id")
    Collection<Comment> findByArticleId(@Param("id") int id);

    @Query("SELECT c FROM Comment c WHERE c.author.id = :id")
    Collection<Comment> findByAuthorId(@Param("id") int authorId);

}
