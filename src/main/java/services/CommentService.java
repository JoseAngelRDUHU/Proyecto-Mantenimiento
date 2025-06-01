package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ArticleRepository;
import repositories.CommentRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public void create(String text, int articleId, Actor author) {
        Article article = articleRepository.findOne(articleId);
        Assert.notNull(article);
        Assert.isTrue(article.getCommentsEnabled());
        Comment c = new Comment();
        c.setText(text);
        c.setArticle(article);
        c.setAuthor(author);
        c.setPublicationMoment(new Date());
        commentRepository.save(c);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
        commentRepository.flush();
    }

    public Comment findOne(int id) {
        return commentRepository.findOne(id);
    }

    public Collection<Comment> findByArticleId(int articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public boolean canEditOrDelete(Comment comment, Actor actor) {
        return comment.getAuthor().getId() == actor.getId()
                || comment.getArticle().getAuthor().getId() == actor.getId()
                || actor instanceof Administrator;
    }
}
