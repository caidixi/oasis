package cmcm.oasis.module;

import javax.persistence.*;

@Entity
public class Author__Paper {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int orderNumber;

    @Column(nullable = false)
    private int reference;

    @Column(nullable = false)
    private int authorNumber;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "paper_DOI")
    private Paper paper;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "author_authorId")
    private Author author;

    public Author__Paper() {
    }

    public Author__Paper(Paper paper, Author author,int orderNumber,int reference,int authorNumber) {
        this.paper = paper;
        this.author = author;
        this.orderNumber = orderNumber;
        this.reference = reference;
        this.authorNumber = authorNumber;
    }

    public Long getId() {
        return id;
    }

    public Paper getPaper() {
        return paper;
    }

    public Author getAuthor() {
        return author;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getReference() {
        return reference;
    }

    public int getAuthorNumber() {
        return authorNumber;
    }
}
