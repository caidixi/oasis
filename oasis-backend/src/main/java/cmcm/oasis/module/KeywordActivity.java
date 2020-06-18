package cmcm.oasis.module;

import javax.persistence.*;


@Entity
public class KeywordActivity {

    @Id
    private Long keywordId;

    @Column(nullable = false,unique = true)
    private String word;

    @Column(nullable = false)
    private double activity;

    @Column(nullable = false)
    private int citation;

    @Column(nullable = false)
    private int publication;

    public KeywordActivity() {
    }

    public KeywordActivity(Long keywordId, String word, double activity, int citation, int publication) {
        this.keywordId = keywordId;
        this.word = word;
        this.activity = activity;
        this.citation = citation;
        this.publication = publication;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public String getWord() {
        return word;
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
