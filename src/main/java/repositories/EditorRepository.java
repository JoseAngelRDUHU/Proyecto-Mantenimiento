package repositories;

import domain.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Integer> {

    @Query("SELECT COUNT(a) FROM Article a WHERE a.author.id = :authorId")
    int countByAuthorId(@Param("authorId") Integer authorId);

}
