package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Comment(){
        super();
    }

    // Attributes -------------------------------------------------------------

    private String text;
    private Date publicationMoment;

    @NotBlank
    @Column(length = 500)
    @Size(max = 500)
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getPublicationMoment() { return publicationMoment; }
    public void setPublicationMoment(Date publicationMoment) { this.publicationMoment = publicationMoment; }

    // Relationships ----------------------------------------------------------

    private Actor author;

    @ManyToOne(optional = false)
    public Actor getAuthor() { return author; }
    public void setAuthor(Actor author) { this.author = author; }

    private Article article;

    @ManyToOne(optional = false)
    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
}
