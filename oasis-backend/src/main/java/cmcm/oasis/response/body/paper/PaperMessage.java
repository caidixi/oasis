package cmcm.oasis.response.body.paper;

public class PaperMessage {
    private String DOI;
    private String title;
    private String authors;
    private int referenceCount;
    private int publicationYear;

    public PaperMessage() {
    }

    public PaperMessage(String DOI, String title, String authors, int referenceCount, int publicationYear) {
        this.DOI = DOI;
        this.title = title;
        this.authors = authors;
        this.referenceCount = referenceCount;
        this.publicationYear = publicationYear;
    }

    public String getDOI() {
        return DOI;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
