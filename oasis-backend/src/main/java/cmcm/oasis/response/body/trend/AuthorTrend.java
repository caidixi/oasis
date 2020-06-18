package cmcm.oasis.response.body.trend;

public class AuthorTrend {
    private String name;
    private String id;
    private double activity;
    private int citation;
    private int publication;
    private int firstAuthor;
    private int hindex;
    private int gindex;
    private double hgindex;

    public AuthorTrend(String name, String id, double activity, int citation, int publication, int firstAuthor, int hindex, int gindex, double hgindex) {
        this.name = name;
        this.id = id;
        this.activity = activity;
        this.citation = citation;
        this.publication = publication;
        this.firstAuthor = firstAuthor;
        this.hindex = hindex;
        this.gindex = gindex;
        this.hgindex = hgindex;
    }

    public AuthorTrend() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
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
