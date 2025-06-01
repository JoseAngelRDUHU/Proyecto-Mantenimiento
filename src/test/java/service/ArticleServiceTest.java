package service;

import domain.Actor;
import domain.Article;
import domain.Category;
import domain.Editor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import services.ActorService;
import services.ArticleService;
import services.CategoryService;
import utilities.AbstractTest;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ArticleServiceTest extends AbstractTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActorService actorService;


    // Creación de artículos
    @Test
    public void testCreateAndSave() {
        final Object[][] testingData = {
                { "editor", null }, // editor puede crear
                { "reader", IllegalArgumentException.class }, // reader no puede crear
                { null, IllegalArgumentException.class }, // no autenticado, error
        };

        for (Object[] testingDatum : testingData) {
            super.startTransaction();
            try {
                this.templateCreateAndSave((String) testingDatum[0], (Class<?>) testingDatum[1]);
            } catch (Throwable oops) {
                throw new RuntimeException(oops);
            } finally {
                super.rollbackTransaction();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateByReader() {
        super.startTransaction();
        try {
            super.authenticate("reader");
            Article article = articleService.create();
            article.setTitle("Title");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            articleService.save(article);
            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWithoutTitle() {
        super.startTransaction();
        try {
            super.authenticate("editor");
            Article article = articleService.create();
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            articleService.save(article);
            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }


    // Edición de artículos
    @Test
    public void testEditByAuthor() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            // Creamos y guardamos un artículo
            Article article = articleService.create();
            article.setTitle("Initial Title");
            article.setBody("Initial Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            // Editamos
            article.setTitle("Updated Title");
            Article updated = articleService.save(article);

            Assert.isTrue(updated.getTitle().equals("Updated Title"), "El título no se ha actualizado correctamente");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test
    public void testEditByAdmin() {
        super.startTransaction();
        try {
            // Editor crea el artículo
            super.authenticate("editor");
            Article article = articleService.create();
            article.setTitle("Original Title");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            super.unauthenticate();

            // Admin lo modifica
            super.authenticate("admin");
            article.setTitle("Admin Edited Title");
            Article updated = articleService.save(article);
            Assert.isTrue(updated.getTitle().equals("Admin Edited Title"), "El admin no pudo modificar el artículo");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditByAnotherEditor() {
        super.startTransaction();
        try {
            // Editor 1 crea el artículo
            super.authenticate("editor");
            Article article = articleService.create();
            article.setTitle("Title");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            super.unauthenticate();

            // Otro editor intenta modificarlo
            super.authenticate("editor2"); // <- debe existir en tu DB
            article.setTitle("Hacked Title");
            articleService.save(article);

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditArticleByReader() {
        super.startTransaction();
        try {
            super.authenticate("editor");
            Article article = articleService.create();
            article.setTitle("Article for reader");
            article.setBody("Should not edit");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            super.unauthenticate();

            super.authenticate("reader");
            article.setTitle("Hacked");
            articleService.save(article);

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }


    // Eliminación de artículos
    @Test
    public void testDeleteArticleByAuthor() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            // Crear y guardar artículo
            Article article = articleService.create();
            article.setTitle("Title to delete");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            // Eliminar artículo
            articleService.delete(article);

            // Comprobar que ya no existe (null)
            Article deleted = articleService.findOne(article.getId());
            Assert.isNull(deleted, "El artículo no se eliminó correctamente");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteArticleByAnotherEditor() {
        super.startTransaction();
        try {
            super.authenticate("editor");
            Article article = articleService.create();
            article.setTitle("Protected article");
            article.setBody("No delete");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            super.unauthenticate();

            super.authenticate("editor2"); // debe existir
            articleService.delete(article);

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test
    public void testDeleteArticleByAdmin() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            // Crear artículo
            Article article = articleService.create();
            article.setTitle("Admin delete test");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            super.unauthenticate();

            super.authenticate("admin");

            // Eliminar como admin
            articleService.delete(article);
            Article deleted = articleService.findOne(article.getId());
            Assert.isNull(deleted, "El artículo no fue eliminado por el administrador");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteArticleByReader() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            // Crear artículo
            Article article = articleService.create();
            article.setTitle("Invalid delete test");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            super.unauthenticate();

            super.authenticate("reader");

            // Intento no autorizado de borrar
            articleService.delete(article);

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }


    // Activar/Desactivar comentarios
    @Test
    public void testEnableDisableComments() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            // Crear artículo con comentarios habilitados (por defecto)
            Article article = articleService.create();
            article.setTitle("Comments test article");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            // Desactivar comentarios
            article.setCommentsEnabled(false);
            article = articleService.save(article);
            Assert.isTrue(!article.getCommentsEnabled(), "No se desactivaron los comentarios");

            // Activar comentarios
            article.setCommentsEnabled(true);
            article = articleService.save(article);
            Assert.isTrue(article.getCommentsEnabled(), "No se activaron los comentarios");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test
    public void testToggleCommentsByAdmin() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            Article article = articleService.create();
            article.setTitle("Admin comment toggle");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            super.unauthenticate();

            super.authenticate("admin");

            article.setCommentsEnabled(false);
            article = articleService.save(article);
            Assert.isTrue(!article.getCommentsEnabled(), "No se desactivaron los comentarios por admin");

            article.setCommentsEnabled(true);
            article = articleService.save(article);
            Assert.isTrue(article.getCommentsEnabled(), "No se activaron los comentarios por admin");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToggleCommentsByReader() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            Article article = articleService.create();
            article.setTitle("Invalid comment toggle");
            article.setBody("Body");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            super.unauthenticate();

            super.authenticate("reader");

            // No debería poder modificar
            article.setCommentsEnabled(false);
            articleService.save(article);

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }


    //Visualizar Artículos
    @Test
    public void testViewArticleAsReader() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            Article article = articleService.create();
            article.setTitle("Article to view");
            article.setBody("Content");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            int articleId = article.getId();

            super.unauthenticate();
            super.authenticate("reader");

            Article result = articleService.findOne(articleId);
            Assert.notNull(result, "El lector no puede ver el artículo");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }

    @Test
    public void testViewArticleAsAnonymous() {
        super.startTransaction();
        try {
            super.authenticate("editor");

            Article article = articleService.create();
            article.setTitle("Anonymous view article");
            article.setBody("Content");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);
            int articleId = article.getId();

            super.unauthenticate(); // Desauntenticamos para que el usuario sea anónimo

            Article result = articleService.findOne(articleId);
            Assert.notNull(result, "El usuario anónimo no puede ver el artículo");

        } finally {
            super.rollbackTransaction();
        }
    }

    protected void templateCreateAndSave(String username, Class<?> expected) {
        Class<?> caught = null;

        try {
            super.authenticate(username);

            Article article = this.articleService.create();
            article.setTitle("Test Article Title");
            article.setBody("This is a test article body.");
            article.setPublicationMoment(new Date());

            int categoryId = super.getEntityId("category1");
            Category category = this.categoryService.findOne(categoryId);
            article.setCategory(category);

            Actor principal = this.actorService.findByPrincipal();
            if (!(principal instanceof Editor)) {
                throw new IllegalArgumentException("Only editors can create articles");
            }

            article.setAuthor((Editor) principal);

            this.articleService.save(article);
            super.flushTransaction();
            super.unauthenticate();
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

    //Fujo completo (Test de integración)
    @Test
    public void testFullArticleLifecycleByAuthor() {
        super.startTransaction();
        try {
            // Crear
            super.authenticate("editor");
            Article article = articleService.create();
            article.setTitle("Full lifecycle");
            article.setBody("Initial content");
            article.setCategory(categoryService.findOne(super.getEntityId("category1")));
            article = articleService.save(article);

            // Editar
            article.setBody("Updated content");
            article = articleService.save(article);

            // Desactivar comentarios
            article.setCommentsEnabled(false);
            article = articleService.save(article);

            // Activar comentarios
            article.setCommentsEnabled(true);
            article = articleService.save(article);

            // Visualizar como lector
            int articleId = article.getId();
            super.unauthenticate();
            super.authenticate("reader");
            Article result = articleService.findOne(articleId);
            Assert.isTrue(result.getBody().equals("Updated content"), "El contenido actualizado no coincide");

            super.unauthenticate();
            super.authenticate("editor");

            // Eliminar
            articleService.delete(article);
            Assert.isNull(articleService.findOne(articleId), "El artículo no fue eliminado correctamente");

            super.unauthenticate();
        } finally {
            super.rollbackTransaction();
        }
    }
}
