package domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Category(){
        super();

        articles = new HashSet<Article>();
    }

    // Attributes -------------------------------------------------------------

    private String name;
    private String description;

    @NotBlank
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Relationships ----------------------------------------------------------


    private Collection<Article> articles;

    @OneToMany(mappedBy = "category")
    public Collection<Article> getArticles() { return articles; }
    public void setArticles(Collection<Article> articles) { this.articles = articles; }
}
