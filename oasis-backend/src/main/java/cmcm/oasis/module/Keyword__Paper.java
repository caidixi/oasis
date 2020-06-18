package cmcm.oasis.module;

import javax.persistence.*;

@Entity
public class Keyword__Paper {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "paper_DOI")
    private Paper paper;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "keyword_keywordId",referencedColumnName = "keywordId")
    private Keyword keyword;

    @Column(nullable = false)
    private int reference;

    public Keyword__Paper() {
    }

    public Keyword__Paper(Paper paper, Keyword keyword,int reference) {
        this.paper = paper;
        this.keyword = keyword;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public Paper getPaper() {
        return paper;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public int getReference() {
        return reference;
    }
}
