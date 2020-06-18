package cmcm.oasis.response.body.graph;

public class AuthorLink {
    private long source;
    private long target;
    private int weight;

    public AuthorLink() {
    }

    public AuthorLink(long source, long target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public long getSource() {
        return source;
    }

    public long getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }
}
