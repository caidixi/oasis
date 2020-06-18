package cmcm.oasis.module;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conference {
    @Id
    @GeneratedValue
    private Long conferenceId;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String name;

    @OneToMany(mappedBy  = "conference",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Paper> paperList;

    public Conference() {
    }

    public Conference(String name) {
        this.name = name;
        paperList = new ArrayList<>();
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public String getName() {
        return name;
    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }
}
