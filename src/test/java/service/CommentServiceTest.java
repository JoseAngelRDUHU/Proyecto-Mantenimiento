package service;

import domain.Actor;
import domain.Article;
import domain.Comment;
import domain.Editor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.ActorService;
import services.ArticleService;
import services.CategoryService;
import services.CommentService;
import utilities.AbstractTest;

import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CommentServiceTest extends AbstractTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private CategoryService categoryService;

    // Crear Comentario
    @Test
    public void testCreateCommentSuccess() {
        super.authenticate("reader");

        Actor author = actorService.findByPrincipal();
        Collection<Article> articles = articleService.findAll();

        // Buscamos un comentario
        Article article = articles.stream()
                .filter(Article::getCommentsEnabled)
                .findFirst()
                .orElse(null);

        assertNotNull(article);

        // Añadimos un comentario
        commentService.create("Comentario test", article.getId(), author);

        // Buscamos que el comentario se añadiese
        Collection<Comment> comments = commentService.findByArticleId(article.getId());
        boolean found = comments.stream().anyMatch(c -> "Comentario test".equals(c.getText()) && c.getAuthor().getId() == author.getId());

        assertTrue(found);

        super.unauthenticate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCommentFailsWhenCommentsDisabled() {
        super.authenticate("editor");

        Article article = new Article();
        article.setTitle("Artículo de prueba sin comentarios");
        article.setBody("Contenido de prueba");
        article.setCommentsEnabled(false);
        article.setCategory(categoryService.findAll().iterator().next());
        article.setAuthor((Editor) actorService.findByPrincipal());
        article.setPublicationMoment( new Date());

        article = articleService.save(article);
        super.unauthenticate();

        super.authenticate("reader");
        Actor author = actorService.findByPrincipal();

        commentService.create("Comentario imposible", article.getId(), author); // debe fallar

        super.unauthenticate();
    }

    // Guardar Comentarios editados (actualizar)
    @Test
    public void testSaveComment() {
        super.authenticate("reader");

        Actor author = actorService.findByPrincipal();

        Article article = articleService.findAll().stream().filter(Article::getCommentsEnabled).findFirst().get();
        commentService.create("Comentario para editar", article.getId(), author);

        Comment comment = commentService.findByArticleId(article.getId()).stream()
                .filter(c -> "Comentario para editar".equals(c.getText()) && c.getAuthor().getId() == author.getId())
                .findFirst().get();

        comment.setText("Comentario editado");

        commentService.save(comment);

        Comment updated = commentService.findOne(comment.getId());
        assertEquals("Comentario editado", updated.getText());

        super.unauthenticate();
    }

    //Borrar Comentario
    @Test
    public void testDeleteComment() {
        super.authenticate("reader");

        Actor author = actorService.findByPrincipal();
        Article article = articleService.findAll().stream()
                .filter(Article::getCommentsEnabled)
                .findFirst()
                .get();
        commentService.create("Comentario a borrar", article.getId(), author);

        Comment comment = commentService.findByArticleId(article.getId()).stream()
                .filter(c -> "Comentario a borrar".equals(c.getText()) && c.getAuthor().getId() == author.getId())
                .findFirst()
                .get();

        commentService.delete(comment);


        assertNull(commentService.findOne(comment.getId()));

        super.unauthenticate();
    }

    // Buscar Comentario
    @Test
    public void testFindOne() {
        super.authenticate("reader");

        Actor author = actorService.findByPrincipal();
        Article article = articleService.findAll().stream().filter(Article::getCommentsEnabled).findFirst().get();
        commentService.create("Comentario para findOne", article.getId(), author);

        Comment comment = commentService.findByArticleId(article.getId()).stream()
                .filter(c -> "Comentario para findOne".equals(c.getText()) && c.getAuthor().getId() == author.getId())
                .findFirst().get();

        Comment found = commentService.findOne(comment.getId());
        assertNotNull(found);
        assertEquals(comment.getText(), found.getText());

        super.unauthenticate();
    }

    @Test
    public void testFindByArticleId() {
        super.authenticate("reader");

        Article article = articleService.findAll().stream().filter(Article::getCommentsEnabled).findFirst().get();

        Collection<Comment> comments = commentService.findByArticleId(article.getId());

        assertNotNull(comments);

        super.unauthenticate();
    }

    // Permisos editar/borrar
    @Test
    public void testCanEditOrDelete() {
        super.authenticate("reader");

        Actor author = actorService.findByPrincipal();
        Article article = articleService.findAll().stream().filter(Article::getCommentsEnabled).findFirst().get();

        commentService.create("Comentario para permisos", article.getId(), author);

        Comment comment = commentService.findByArticleId(article.getId()).stream()
                .filter(c -> "Comentario para permisos".equals(c.getText()) && c.getAuthor().getId() == author.getId())
                .findFirst().get();

        assertTrue(commentService.canEditOrDelete(comment, author));

        Actor articleAuthor = article.getAuthor();
        assertTrue(commentService.canEditOrDelete(comment, articleAuthor));

        super.authenticate("admin");
        Actor admin = actorService.findByPrincipal();
        assertTrue(commentService.canEditOrDelete(comment, admin));

        super.authenticate("reader2");
        Actor other = actorService.findByPrincipal();
        assertFalse(commentService.canEditOrDelete(comment, other));

        super.unauthenticate();
    }
}
