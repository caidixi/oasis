package cmcm.oasis.module;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PaperActivity {

    @Id
    private String DOI;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String documentTitle;

    @Column(nullable = false)
    private int citation;

    public PaperActivity() {
    }

    public PaperActivity(String DOI, String documentTitle, double activity, int citation) {
        this.DOI = DOI;
        this.documentTitle = documentTitle;
        this.citation = citation;
    }

    public String getDOI() {
        return DOI;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public int getCitation() {
        return citation;
    }
}
