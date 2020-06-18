package cmcm.oasis.response.body.paper;

public class PublicationOfYear {
    private int year;
    private int publicationCount;

    public PublicationOfYear() {
    }

    public PublicationOfYear(int year, int publicationCount) {
        this.year = year;
        this.publicationCount = publicationCount;
    }

    public int getYear() {
        return year;
    }

    public int getPublicationCount() {
        return publicationCount;
    }
}
