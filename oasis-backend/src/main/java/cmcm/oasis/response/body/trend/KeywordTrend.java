package cmcm.oasis.response.body.trend;

public class KeywordTrend {
    private String name;
    private String id;
    private double activity;
    private int citation;
    private int publication;

    public KeywordTrend(String name, String id, double activity, int citation, int publication) {
        this.name = name;
        this.id = id;
        this.activity = activity;
        this.citation = citation;
        this.publication = publication;
    }

    public KeywordTrend() {
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
}
