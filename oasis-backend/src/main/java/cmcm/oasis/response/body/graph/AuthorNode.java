package cmcm.oasis.response.body.graph;

public class AuthorNode {
    private long id;
    private String name;
    private String affiliation;
    private double activity;

    public AuthorNode() {
    }

    public AuthorNode(long id, String name, String affiliation, double activity) {
        this.id = id;
        this.name = name;
        this.affiliation = affiliation;
        this.activity = activity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public double getActivity() {
        return activity;
    }
}
