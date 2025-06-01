package service;

import domain.Reader;
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
import services.WelcomeService;
import utilities.AbstractTest;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class WelcomeServiceTest extends AbstractTest {

    @Autowired
    WelcomeService welcomeService;

    @Autowired
    ActorService actorService;

    // Crear un nuevo Reader
    @Test
    public void testSaveNewReader() {
        Reader reader = new Reader();
        UserAccount account = new UserAccount();
        account.setUsername("nuevoReader");
        account.setPassword("clave123");

        Authority authority = new Authority();
        authority.setAuthority(Authority.READER);
        account.setAuthorities(Collections.singletonList(authority));

        reader.setUserAccount(account);
        reader.setName("Nombre");
        reader.setEmail("email@acme.com");
        reader.setPhone("+34 123456789");

        Reader saved = welcomeService.save(reader);

        assertNotNull(saved);
        assertTrue(saved.getId() != 0);
        assertEquals(DigestUtils.md5DigestAsHex("clave123".getBytes()), saved.getUserAccount().getPassword());
    }

    // Guardar un lector ya persistido (error)
    @Test(expected = IllegalArgumentException.class)
    public void testSaveFailsIfNotNew() {
        super.authenticate("reader");

        Reader reader = (Reader) actorService.findByPrincipal();
        welcomeService.save(reader);

        super.unauthenticate();
    }

    // Buscar por ID
    @Test
    public void testFindOne() {
        super.authenticate("reader");

        Reader reader = (Reader) actorService.findByPrincipal();

        Reader found = welcomeService.findOne(reader.getId());

        assertNotNull(found);
        assertEquals(reader.getId(), found.getId());

        super.unauthenticate();
    }
}
