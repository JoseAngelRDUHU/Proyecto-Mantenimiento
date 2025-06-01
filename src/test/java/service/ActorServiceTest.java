package service;


import domain.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.ActorService;
import utilities.AbstractTest;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest {

    @Autowired
    private ActorService actorService;

    // Busqueda de usuario
    @Test
    public void testFindByPrincipal_asAdmin() {
        super.authenticate("admin");

        Actor principal = actorService.findByPrincipal();

        assertNotNull(principal);
        assertEquals("admin", principal.getUserAccount().getUsername());

        super.unauthenticate();
    }

    @Test
    public void testFindByPrincipal_asEditor() {
        super.authenticate("editor");

        Actor principal = actorService.findByPrincipal();

        assertNotNull(principal);
        assertEquals("editor", principal.getUserAccount().getUsername());

        super.unauthenticate();
    }

    @Test
    public void testFindByPrincipal_asReader() {
        super.authenticate("reader");

        Actor principal = actorService.findByPrincipal();

        assertNotNull(principal);
        assertEquals("reader", principal.getUserAccount().getUsername());

        super.unauthenticate();
    }

    // Test de permisos de administrador
    @Test
    public void testIsAdmin_true() {
        super.authenticate("admin");

        boolean isAdmin = actorService.isAdmin();

        assertTrue(isAdmin);

        super.unauthenticate();
    }

    @Test
    public void testIsAdmin_falseForEditor() {
        super.authenticate("editor");

        boolean isAdmin = actorService.isAdmin();

        assertFalse(isAdmin);

        super.unauthenticate();
    }

    @Test
    public void testIsAdmin_falseForReader() {
        super.authenticate("reader");

        boolean isAdmin = actorService.isAdmin();

        assertFalse(isAdmin);

        super.unauthenticate();
    }
}
