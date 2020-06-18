package cmcm.oasis.module;

import javax.persistence.*;

@Entity
public class AuthorSide {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "source_id")
    private Author source;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "target_id")
    private Author target;

    @Column(nullable = false)
    private int weight;

    public AuthorSide() {
    }

    public AuthorSide(Author source, Author target) {
        this.source = source;
        this.target = target;
        this.weight = 0;
    }

    public Long getId() {
        return id;
    }

    public Author getSource() {
        return source;
    }

    public Author getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    public void addWeight() {
        this.weight++;
    }
}
