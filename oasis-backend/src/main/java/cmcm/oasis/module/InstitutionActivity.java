package cmcm.oasis.module;

import javax.persistence.*;
import java.util.List;

@Entity
public class InstitutionActivity {
    @Id
    private Long institutionId;

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

    public InstitutionActivity() {
    }

    public InstitutionActivity(Long institutionId, String name, double activity, int citation, int publication, int firstAuthor, int hindex, int gindex, double hgindex) {
        this.institutionId = institutionId;
        this.name = name;
        this.activity = activity;
        this.citation = citation;
        this.publication = publication;
        this.firstAuthor = firstAuthor;
        this.hindex = hindex;
        this.gindex = gindex;
        this.hgindex = hgindex;
    }

    public Long getInstitutionId() {
        return institutionId;
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
