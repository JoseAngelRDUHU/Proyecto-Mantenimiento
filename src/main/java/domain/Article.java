package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
@Access(AccessType.PROPERTY)
public class Article extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Article(){
        super();

        comments = new HashSet<Comment>();
    }
    // Attributes -------------------------------------------------------------

    private String title;
    private String body;
    private Date publicationMoment;
    private boolean commentsEnabled = true;
    private String coverImageUrl;
    private String videoLink;

    @NotNull
    @NotBlank
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Lob
    @NotBlank
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getPublicationMoment() { return publicationMoment; }
    public void setPublicationMoment(Date publicationMoment) { this.publicationMoment = publicationMoment; }

    public boolean getCommentsEnabled() { return commentsEnabled; }
    public void setCommentsEnabled(boolean commentsEnabled) { this.commentsEnabled = commentsEnabled; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public String getVideoLink() { return videoLink; }
    public void setVideoLink(String videoLink) { this.videoLink = videoLink; }



    // Relationships ----------------------------------------------------------

    private Editor author;

    @ManyToOne(optional = false)
    public Editor getAuthor() { return author; }
    public void setAuthor(Editor author) { this.author = author; }

    private Category category;

    @ManyToOne(optional = false)
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    private Collection<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    public Collection<Comment> getComments() { return comments; }
    public void setComments(Collection<Comment> comments) { this.comments = comments; }
}
