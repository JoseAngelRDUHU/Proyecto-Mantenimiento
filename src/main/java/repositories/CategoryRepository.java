package repositories;

import domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Buscar categoria por el nombre
    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    Category findByName(String categoryName);

    // Buscar categoria por la id
    @Query("SELECT c FROM Category c WHERE c.id = ?1")
    Category findById(int categoryID);
}
