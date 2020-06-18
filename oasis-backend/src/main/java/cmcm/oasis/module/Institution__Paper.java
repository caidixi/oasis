package cmcm.oasis.module;

import javax.persistence.*;

@Entity
public class Institution__Paper {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "paper_DOI")
    private Paper paper;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "institution_institutionId")
    private Institution institution;

    @Column(nullable = false)
    private int orderNumber;

    @Column(nullable = false)
    private int reference;

    public Institution__Paper() {
    }

    public Institution__Paper(Paper paper, Institution institution,int orderNumber, int reference ) {
        this.orderNumber = orderNumber;
        this.reference = reference;
        this.paper = paper;
        this.institution = institution;
    }

    public Long getId() {
        return id;
    }

    public Paper getPaper() {
        return paper;
    }

    public Institution getInstitution() {
        return institution;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getReference() {
        return reference;
    }
}
