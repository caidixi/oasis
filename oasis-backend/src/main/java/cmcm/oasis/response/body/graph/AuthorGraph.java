package cmcm.oasis.response.body.graph;



import java.util.LinkedList;
import java.util.List;

public class AuthorGraph {
    private List<AuthorNode> nodes;
    private List<AuthorLink> links;

    public AuthorGraph() {
        this.nodes = new LinkedList<>();
        this.links = new LinkedList<>();
    }

    public void addNodes(AuthorNode node) {
        this.nodes.add(node);
    }

    public void addLinks(AuthorLink link) {
        this.links.add(link);
    }

    public List<AuthorNode> getNodes() {
        return nodes;
    }

    public List<AuthorLink> getLinks() {
        return links;
    }
}
