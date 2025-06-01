package controllers;

import domain.Actor;
import domain.Article;
import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ArticleService;
import services.CommentService;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam int articleId, @RequestParam String text) {
        Actor principal = actorService.findByPrincipal();
        commentService.create(text, articleId, principal);
        return "redirect:/article/view.do?articleId=" + articleId;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam int commentId, @RequestParam String text) {
        Actor principal = actorService.findByPrincipal();
        Comment comment = commentService.findOne(commentId);
        Assert.isTrue(commentService.canEditOrDelete(comment, principal));
        comment.setText(text);
        commentService.save(comment);
        return "redirect:/article/view.do?articleId=" + comment.getArticle().getId();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int commentId) {
        Actor principal = actorService.findByPrincipal();
        Comment comment = commentService.findOne(commentId);
        Assert.isTrue(commentService.canEditOrDelete(comment, principal), "No autorizado");
        commentService.delete(comment);
        return "redirect:/article/view.do?articleId=" + comment.getArticle().getId();
    }
}

