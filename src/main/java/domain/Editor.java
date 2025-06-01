package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Access(AccessType.PROPERTY)
public class Editor extends Actor{

    // Constructors -----------------------------------------------------------

    public Editor(){
        super();

        articles = new HashSet<Article>();
    }

    // Attributes -------------------------------------------------------------

    // Relationships ----------------------------------------------------------


    private Collection<Article> articles;

    @OneToMany(mappedBy = "author")
    public Collection<Article> getArticles() { return articles; }
    public void setArticles(Collection<Article> articles) { this.articles = articles; }

}
