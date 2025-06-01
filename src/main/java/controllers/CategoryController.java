package controllers;

import domain.Actor;
import domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.CategoryService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("category/list");
        Collection<Category> categories = categoryService.findAll();
        Actor principal = actorService.findByPrincipal();
        boolean isAdmin = actorService.isAdmin();

        result.addObject("categories", categories);
        result.addObject("isAdmin", isAdmin);
        result.addObject("newCategory", new Category()); // para el formulario

        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("newCategory") Category category) {
        categoryService.save(category);
        return "redirect:/category/list.do";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int categoryId) {
        Category category = categoryService.findOne(categoryId);
        if (category != null) {
            categoryService.delete(category);
        }
        return "redirect:/category/list.do";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Category category) {
        Category modcategory = categoryService.findOne(category.getId());
        modcategory.setName(category.getName());
        modcategory.setDescription(category.getDescription());
        categoryService.save(modcategory);
        return "redirect:/category/list.do";
    }
}
