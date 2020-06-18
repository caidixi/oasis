package cmcm.oasis.module;

import javax.persistence.*;

@Entity
public class AuthorActivity {
    @Id
    private Long authorId;

    @Column(nullable = false, columnDefinition = "text character set utf8")
    private String name;

    @Column(nullable = false)
    private double activity;

    @Column(nullable = false)
    private int citation;

    @Column(nullable = false)
    private int publication;

    @Column(nullable = false)
    private int firstAuthor;

    @Column(nullable = false)
    private int hindex;

    @Column(nullable = false)
    private int gindex;

    @Column(nullable = false)
    private double hgindex;

    public AuthorActivity() {
    }

    public AuthorActivity(Long authorId, String name, double activity, int citation, int publication, int firstAuthor, int hindex, int gindex, double hgindex) {
        this.authorId = authorId;
        this.name = name;
        this.activity = activity;
        this.citation = citation;
        this.publication = publication;
        this.firstAuthor = firstAuthor;
        this.hindex = hindex;
        this.gindex = gindex;
        this.hgindex = hgindex;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public double getActivity() {
        return activity;
    }

    public int getCitation() {
        return citation;
    }

    public int getPublication() {
        return publication;
    }

    public int getFirstAuthor() {
        return firstAuthor;
    }

    public int getHindex() {
        return hindex;
    }

    public int getGindex() {
        return gindex;
    }

    public double getHgindex() {
        return hgindex;
    }
}
