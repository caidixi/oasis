package cmcm.oasis.module;

import javax.persistence.*;
import java.util.List;

@Entity
public class Institution {
    @Id
    @GeneratedValue
    private Long institutionId;

    @Column(nullable = false, columnDefinition = "text character set utf8")
    private String name;

    @OneToMany(mappedBy  = "institution",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Institution__Paper> institution__papers;

    @OneToMany(mappedBy  = "institution",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Author> authors;

    public Institution() {
    }

    public Institution(String name) {
        this.name = name;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public String getName() {
        return name;
    }

    public List<Institution__Paper> getInstitution__papers() {
        return institution__papers;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
