package cmcm.oasis.module;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Keyword {

    @Id
    @GeneratedValue
    private Long keywordId;

    @Column(nullable = false,unique = true)
    private String word;

    @OneToMany(mappedBy  = "keyword",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Keyword__Paper> keyword__papers;

    public Keyword() {
    }

    public Keyword(String word) {
        this.word = word;
        keyword__papers = new ArrayList<>();
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public String getWord() {
        return word;
    }

    public List<Keyword__Paper> getKeyword__papers() {
        return keyword__papers;
    }
}
