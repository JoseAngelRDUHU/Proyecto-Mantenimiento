package repositories;

import domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelcomeRepository extends JpaRepository<Reader, Integer> {


}
