package cmcm.oasis.response.body;

public class NameAndId {
    private String name;
    private String id;
    private double activity;
    private int paperCount;
    private int citationCount;

    public NameAndId() {
    }

    public NameAndId(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public NameAndId(String name, long id) {
        this.name = name;
        this.id = id+"";
    }

    public NameAndId(String name, Long id, double activity) {
        this.name = name;
        this.id = id+"";
        this.activity = activity;
    }

    public NameAndId(String name, String id, double activity) {
        this.name = name;
        this.id = id;
        this.activity = activity;
    }

    public NameAndId(String name, String id, double activity, int paperCount) {
        this.name = name;
        this.id = id;
        this.activity = activity;
        this.paperCount = paperCount;
    }

    public NameAndId(String name, Long id, double activity, int paperCount) {
        this.name = name;
        this.id = id+"";
        this.activity = activity;
        this.paperCount = paperCount;
    }

    public NameAndId(String name, Long id, double activity, int paperCount, int citationCount) {
        this.name = name;
        this.id = id+"";
        this.activity = activity;
        this.paperCount = paperCount;
        this.citationCount = citationCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public int getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(int citationCount) {
        this.citationCount = citationCount;
    }
}
