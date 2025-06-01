package service;

import domain.Editor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.EditorService;
import utilities.AbstractTest;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class EditorServiceTest extends AbstractTest {

    @Autowired
    private EditorService editorService;

    @Autowired
    private ActorService actorService;

    // Crear Editor
    @Test
    public void testCreate() {
        Editor editor = editorService.create();

        assertNotNull(editor);
        assertNotNull(editor.getUserAccount());
        assertTrue(editor.getUserAccount().getAuthorities().stream()
                .anyMatch(a -> Authority.EDITOR.equals(a.getAuthority())));
    }

    // Guardar Editor
    @Test
    public void testSaveNewEditor() {
        super.authenticate("admin");

        Editor editor = editorService.create();
        editor.setName("EditorTest");
        editor.setEmail("nuevo@editor.com");
        editor.setPhone("+34 123456789");

        UserAccount ua = editor.getUserAccount();
        ua.setUsername("editortest");
        ua.setPassword("editortest");

        Editor saved = editorService.save(editor);

        assertEquals(DigestUtils.md5DigestAsHex("editortest".getBytes()), saved.getUserAccount().getPassword());

        super.unauthenticate();
    }

    //Editar existente
    @Test
    public void testUpdateEditorWithNewPassword() {
        super.authenticate("admin");

        Editor editor = editorService.create();
        editor.setName("Editor Test");
        editor.setEmail("editor@test.com");
        editor.setPhone("+31 123456789");
        editor.getUserAccount().setUsername("editorTest");
        editor.getUserAccount().setPassword("initial");

        Editor saved = editorService.save(editor);

        saved.getUserAccount().setPassword("newpass");

        Editor updated = editorService.save(saved);

        assertEquals(
                DigestUtils.md5DigestAsHex("newpass".getBytes()),
                updated.getUserAccount().getPassword()
        );

        super.unauthenticate();
    }

    // Eliminar Editor
    @Test
    public void testDeleteEditor() {
        super.authenticate("admin");

        Editor editor = editorService.create();
        editor.setName("Editor Delete");
        editor.setEmail("delete@test.com");
        editor.setPhone("+31 000000000");

        UserAccount ua = editor.getUserAccount();
        ua.setUsername("editorDelete");
        ua.setPassword("toDelete");

        Editor saved = editorService.save(editor);

        editorService.delete(saved);

        assertNull(editorService.findOne(saved.getId()));

        super.unauthenticate();
    }

    // Buscar todos los editores
    @Test
    public void testFindAll() {
        Collection<Editor> all = editorService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
    }

    // Buscar uno
    @Test
    public void testFindOne() {
        Editor any = editorService.findAll().iterator().next();
        Editor found = editorService.findOne(any.getId());

        assertNotNull(found);
        assertEquals(any.getId(), found.getId());
    }

    // Contar artículos de un editor
    @Test
    public void testCountByAuthorId() {
        super.authenticate("editor");

        Editor editor = (Editor) actorService.findByPrincipal();
        int count = editorService.countByAuthorId(editor.getId());

        assertTrue(count >= 0);

        super.unauthenticate();
    }
}
