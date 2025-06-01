package repositories;

import java.util.Collection;
import domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    // Obtener articulos por autor
    @Query("SELECT a FROM Article a WHERE a.author.id = ?1")
    Collection<Article> findByAuthorId(int authorId);

    // Obtener articulos por categoria
    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
    Collection<Article> findByCategoryId(@Param("categoryId") int categoryId);

    // Buscar articulo por palabra clave en el titulo o en el cuerpo
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.body) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Collection<Article> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId AND " +
            "(LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.body) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Collection<Article> searchByKeywordAndCategory(@Param("keyword") String keyword,
                                                   @Param("categoryId") int categoryId);

    @Query("SELECT a FROM Article a WHERE a.author.id = :authorId AND " +
            "(LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.body) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Collection<Article> searchByKeywordAndAuthor(@Param("keyword") String keyword,
                                                 @Param("authorId") int authorId);

    // Listar articulos ordenados por numero de comentarios descencientes
    @Query("SELECT a FROM Article a LEFT JOIN a.comments c GROUP BY a ORDER BY COUNT(c) DESC")
    Collection<Article> findArticlesOrderByCommentsDesc();

    // Numero medio de comentarios por articulo
    @Query(value = "SELECT AVG(comment_count) FROM (SELECT COUNT(*) AS comment_count FROM comment GROUP BY article_id) AS subquery", nativeQuery = true)
    Double averageCommentsPerArticle();
}
