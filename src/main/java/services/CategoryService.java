package services;

import domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.CategoryRepository;

import java.util.Collection;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActorService actorService;

    public Category create() {
        Assert.isTrue(actorService.isAdmin());
        return new Category();
    }

    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(int id) {
        return categoryRepository.findOne(id);
    }

    public Category save(Category category) {
        Assert.isTrue(actorService.isAdmin());
        return categoryRepository.save(category);
    }

    public void delete(Category category) {
        Assert.isTrue(actorService.isAdmin());
        categoryRepository.delete(category);
    }
}
