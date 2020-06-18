package cmcm.oasis.response.body.paper;

import cmcm.oasis.module.*;
import cmcm.oasis.response.body.NameAndId;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PaperShortcut implements Serializable {

    private String DOI;

    private String documentTitle;

    private List<NameAndId> authorAffiliations;

    private List<NameAndId> authors;

    private int publicationYear;

    private int referenceCount;

    public PaperShortcut(Paper paper) {
        DOI = paper.getDOI();
        documentTitle = paper.getDocumentTitle();
        publicationYear = paper.getPublicationYear();
        referenceCount = paper.getReferenceCount();
    }

    public String getDOI() {
        return DOI;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public List<NameAndId> getAuthorAffiliations() {
        return authorAffiliations;
    }

    public List<NameAndId> getAuthors() {
        return authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setAuthorAffiliations(List<NameAndId> authorAffiliations) {
        this.authorAffiliations = authorAffiliations;
    }

    public void setAuthors(List<NameAndId> authors) {
        this.authors = authors;
    }
}
