package repositories;

import domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    // Busca por nombre de usuario
    @Query("select a from Actor a where a.userAccount.username = ?1")
    Actor findByUsername(String username);
}
