package services;

import domain.Actor;
import domain.Article;
import domain.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ArticleRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ActorService actorService;

    public Article create() {
        Actor principal = this.actorService.findByPrincipal();
        if (!(principal instanceof Editor)) {
            throw new IllegalArgumentException("Only editors can create articles");
        }

        Article result = new Article();
        result.setCommentsEnabled(true);
        result.setPublicationMoment(new Date());
        result.setAuthor((Editor) principal);
        return result;
    }

    public Collection<Article> findAll() {
        Collection<Article> result = articleRepository.findAll();
        Assert.notNull(result);
        return result;
    }

    public Article findOne(int id) {
        return articleRepository.findOne(id);
    }

    public Article save(Article article){
        Assert.notNull(article);
        if (article.getTitle() == null || article.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("The title must not be empty");
        }
        Assert.isTrue(article.getAuthor().equals(actorService.findByPrincipal()) || actorService.isAdmin());
        return articleRepository.save(article);
    }

    public void delete(Article article){
        Assert.notNull(article);
        Assert.isTrue(article.getAuthor().equals(actorService.findByPrincipal()) || actorService.isAdmin());
        articleRepository.delete(article);
    }

    public Collection<Article> findByAuthorId(int authorId) {
        return articleRepository.findByAuthorId(authorId);
    }

    public Collection<Article> findByCategoryId(int categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }

    public Collection<Article> searchByKeyword(String keyword) {
        return articleRepository.searchByKeyword(keyword);
    }

    public Collection<Article> searchByKeywordAndAuthor(String keyword, int authorId) {
        return articleRepository.searchByKeywordAndAuthor(keyword, authorId);
    }

    public Collection<Article> searchByKeywordAndCategory(String keyword, int categoryId) {
        return articleRepository.searchByKeywordAndCategory(keyword, categoryId);
    }

    public Double averageCommentsPerArticle() {
        return articleRepository.averageCommentsPerArticle();
    }

    public Collection<Article> findArticlesOrderByCommentsDesc() {
        return articleRepository.findArticlesOrderByCommentsDesc();
    }
}
