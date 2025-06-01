package controllers;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ArticleService;
import services.CategoryService;
import services.CommentService;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(required = false) Integer categoryId,
                             @RequestParam(required = false) String keyword) {
        ModelAndView result;
        Collection<Article> articles;
        Category category = null;

        if (categoryId != null) {
            category = categoryService.findOne(categoryId);
            Assert.notNull(category);

            if (keyword != null && !keyword.trim().isEmpty()) {
                articles = articleService.searchByKeywordAndCategory(keyword, categoryId);
            } else {
                articles = articleService.findByCategoryId(categoryId);
            }
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            articles = articleService.searchByKeyword(keyword);
        } else {
            articles = articleService.findAll();
        }

        result = new ModelAndView("article/list");
        result.addObject("articles", articles);
        result.addObject("categoryFilter", category);
        result.addObject("keyword", keyword);
        return result;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view(@RequestParam int articleId) {
        ModelAndView result;
        Article article = articleService.findOne(articleId);
        Assert.notNull(article);

        Actor principal = actorService.findByPrincipal();

        boolean canToggle = principal.getId() == article.getAuthor().getId() || actorService.isAdmin();

        Map<Integer, Boolean> canEditOrDeleteComment = new HashMap<>();
        for (Comment c : article.getComments()) {
            boolean allowed = c.getAuthor().getId() == principal.getId()
                    || article.getAuthor().getId() == principal.getId()
                    || actorService.isAdmin();
            canEditOrDeleteComment.put(c.getId(), allowed);
        }

        result = new ModelAndView("article/view");
        result.addObject("article", article);
        result.addObject("canToggleComments", canToggle);
        result.addObject("canEditOrDeleteComment", canEditOrDeleteComment);
        return result;
    }

    @RequestMapping(value = "/toggleComments", method = RequestMethod.POST)
    public String toggleComments(@RequestParam int articleId) {
        Article article = articleService.findOne(articleId);
        Actor principal = actorService.findByPrincipal();
        Assert.isTrue(principal.getId() == article.getAuthor().getId() || actorService.isAdmin());
        article.setCommentsEnabled(!article.getCommentsEnabled());
        articleService.save(article);
        return "redirect:/article/view.do?articleId=" + articleId;
    }

    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    public ModelAndView myList(@RequestParam(required = false) Integer authorId,
                               @RequestParam(required = false) String keyword) {
        Collection<Article> articles;
        Actor actor = actorService.findByPrincipal();

        if (authorId == null) {
            authorId = actor.getId();
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            articles = articleService.searchByKeywordAndAuthor(keyword, authorId);
        } else {
            articles = articleService.findByAuthorId(authorId);
        }

        ModelAndView result = new ModelAndView("article/myList");
        result.addObject("articles", articles);
        result.addObject("authorId", authorId);
        result.addObject("keyword", keyword);
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        Article article = articleService.create();
        ModelAndView result = new ModelAndView("article/edit");
        result.addObject("article", article);
        result.addObject("categories", categoryService.findAll());
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int articleId) {
        Article article = articleService.findOne(articleId);
        Assert.notNull(article);
        Actor principal = actorService.findByPrincipal();
        Assert.isTrue(article.getAuthor().getId() == principal.getId() || actorService.isAdmin());

        ModelAndView result = new ModelAndView("article/edit");
        result.addObject("article", article);
        result.addObject("categories", categoryService.findAll());
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("article") Article article, BindingResult binding) {
        if (binding.hasErrors()) {
            ModelAndView result = new ModelAndView("article/edit");
            result.addObject("article", article);
            result.addObject("categories", categoryService.findAll());
            return result;
        }

        Actor principal = actorService.findByPrincipal();

        if (article.getId() == 0) {
            article.setAuthor((Editor) principal);
            article.setPublicationMoment(new Date());
        } else {
            Article original = articleService.findOne(article.getId());
            Assert.isTrue(original.getAuthor().getId() == principal.getId() || actorService.isAdmin());
            original.setTitle(article.getTitle());
            original.setBody(article.getBody());
            original.setCommentsEnabled(article.getCommentsEnabled());
            original.setCoverImageUrl(article.getCoverImageUrl());
            original.setVideoLink(article.getVideoLink());
            Category fullCategory = categoryService.findOne(article.getCategory().getId());
            Assert.notNull(fullCategory);
            original.setCategory(fullCategory);

            article = original;
        }
        articleService.save(article);
        if(actorService.isAdmin()){
            return new ModelAndView("redirect:/article/myList.do?authorId="+ article.getAuthor().getId());
        }
        return new ModelAndView("redirect:/article/myList.do");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int articleId) {
        Article article = articleService.findOne(articleId);
        Assert.notNull(article);
        Actor principal = actorService.findByPrincipal();
        Assert.isTrue(article.getAuthor().getId() == principal.getId() || actorService.isAdmin());

        articleService.delete(article);
        if(actorService.isAdmin()){
            return new ModelAndView("redirect:/article/myList.do?authorId="+ article.getAuthor().getId());
        }
        return new ModelAndView("redirect:/article/myList.do");
    }

}
