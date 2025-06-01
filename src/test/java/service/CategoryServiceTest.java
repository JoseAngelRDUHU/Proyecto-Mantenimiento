package service;

import domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import repositories.CategoryRepository;
import services.CategoryService;
import utilities.AbstractTest;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CategoryServiceTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Creación de Categoría
    @Test
    public void testCreateAsAdmin() {
        super.authenticate("admin");

        Category c = categoryService.create();

        assertNotNull(c);

        super.unauthenticate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNotAdmin() {
        super.authenticate("editor");

        categoryService.create();

        super.unauthenticate();
    }

    // Listado de Categoría
    @Test
    public void testFindAll() {
        super.authenticate("reader");

        Collection<Category> categories = categoryService.findAll();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());

        super.unauthenticate();
    }

    @Test
    public void testFindOne() {
        super.authenticate("reader");

        Category c = categoryRepository.findAll().iterator().next();
        Category found = categoryService.findOne(c.getId());

        assertNotNull(found);
        assertEquals(c.getId(), found.getId());

        super.unauthenticate();
    }

    // Guardar Categoría
    @Test
    public void testSaveAsAdmin() {
        super.authenticate("admin");

        Category c = new Category();
        c.setName("TestCategory");

        Category saved = categoryService.save(c);

        assertNotNull(saved);
        assertEquals("TestCategory", saved.getName());

        super.unauthenticate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNotAdmin() {
        super.authenticate("reader");

        Category c = new Category();
        c.setName("TestCategory");

        categoryService.save(c);

        super.unauthenticate();
    }

    // Borrar Categoría
    @Test
    public void testDeleteAsAdmin() {
        super.authenticate("admin");

        Category c = new Category();
        c.setName("TestCategoryToDelete");
        c = categoryService.save(c);

        categoryService.delete(c);

        assertNull(categoryService.findOne(c.getId()));

        super.unauthenticate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotAdmin() {
        super.authenticate("editor");

        Category c = categoryRepository.findAll().iterator().next();

        categoryService.delete(c);

        super.unauthenticate();
    }
}
