package services;

import domain.Actor;
import domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ActorRepository;
import security.LoginService;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor findByPrincipal(){
        //simulamos la autentificacion para que nos de el user actual
        return actorRepository.findByUsername(LoginService.getPrincipal().getUsername());
    }

    public boolean isAdmin(){
        return findByPrincipal() instanceof Administrator;
    }
}
