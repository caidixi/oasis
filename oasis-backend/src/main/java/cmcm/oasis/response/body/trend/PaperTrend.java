package cmcm.oasis.response.body.trend;

public class PaperTrend {
    private String name;
    private String id;
    private int citation;

    public PaperTrend(String name, String id, int citation) {
        this.name = name;
        this.id = id;
        this.citation = citation;
    }

    public PaperTrend() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getCitation() {
        return citation;
    }
}
