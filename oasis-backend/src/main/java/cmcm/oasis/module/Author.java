package cmcm.oasis.module;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long authorId;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String ieeeID;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String name;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @OneToMany(mappedBy  = "author",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Author__Paper> author__paperList;

    public Author() {
    }

    public Author(String name, Institution institution) {
        this.name = name;
        this.institution = institution;
        this.ieeeID = "";
        author__paperList = new ArrayList<>();
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<Author__Paper> getAuthor__paperList() {
        return author__paperList;
    }

    public void setAuthor__paperList(List<Author__Paper> author__paperList) {
        this.author__paperList = author__paperList;
    }

    public String getIeeeID() {
        return ieeeID;
    }

    public void setIeeeID(String ieeeID) {
        ieeeID = ieeeID;
    }
}
