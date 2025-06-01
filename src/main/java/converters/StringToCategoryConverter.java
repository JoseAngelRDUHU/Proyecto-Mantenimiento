package converters;

import domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.CategoryService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToCategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category convert(String text) {
        try {
            int id = Integer.parseInt(text);
            Category category = categoryService.findOne(id);
            if (category == null) {
                throw new IllegalArgumentException("No existe categoría con ID " + id);
            }
            return category;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de categoría inválido: " + text);
        }
    }
}
