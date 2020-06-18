package cmcm.oasis.response.body.author;

import cmcm.oasis.response.body.NameAndId;
import cmcm.oasis.response.body.paper.PaperMessage;
import cmcm.oasis.response.body.paper.PublicationOfYear;

import java.util.LinkedList;
import java.util.List;

public class AuthorDetail {
    private String name;
    private Long authorId;
    private String affiliation;
    private Long affiliationId;
    private List<NameAndId> fieldOfResearches;
    private int totalReferenceCount;
    private int totalPublication;
    private double activity;
    private int hindex;
    private int gindex;
    private double hgindex;
    private List<PaperMessage> papers;
    private List<PublicationOfYear> publicationOfYear;

    public AuthorDetail(String name, Long authorId, String affiliation, Long affiliationId) {
        this.name = name;
        this.authorId = authorId;
        this.affiliation = affiliation;
        this.affiliationId = affiliationId;
        fieldOfResearches = new LinkedList<>();
        papers = new LinkedList<>();
        publicationOfYear = new LinkedList<>();
    }

    public AuthorDetail() {
    }

    public void addFieldOfResearch(NameAndId nameAndId){
        fieldOfResearches.add(nameAndId);
    }

    public void addFieldOfResearch(LinkedList<NameAndId> nameAndId){
        fieldOfResearches.addAll(nameAndId);
    }

    public void addPaper(PaperMessage paper){
        papers.add(paper);
    }

    public void addPublicationOfYear(PublicationOfYear publicationOfYear){
        this.publicationOfYear.add(publicationOfYear);
    }

    public void addPublicationOfYear(LinkedList<PublicationOfYear> publicationOfYear){
        this.publicationOfYear.addAll(publicationOfYear);
    }

    public String getName() {
        return name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public Long getAffiliationId() {
        return affiliationId;
    }

    public List<NameAndId> getFieldOfResearchs() {
        return fieldOfResearches;
    }

    public int getTotalReferenceCount() {
        return totalReferenceCount;
    }

    public int getTotalPublication() {
        return totalPublication;
    }

    public List<PaperMessage> getPapers() {
        return papers;
    }

    public List<PublicationOfYear> getPublicationOfYear() {
        return publicationOfYear;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getHindex() {
        return hindex;
    }

    public void setHindex(int hindex) {
        this.hindex = hindex;
    }

    public int getGindex() {
        return gindex;
    }

    public void setGindex(int gindex) {
        this.gindex = gindex;
    }

    public double getHgindex() {
        return hgindex;
    }

    public void setHgindex(double hgindex) {
        this.hgindex = hgindex;
    }

    public void setTotalReferenceCount(int totalReferenceCount) {
        this.totalReferenceCount = totalReferenceCount;
    }

    public void setTotalPublication(int totalPublication) {
        this.totalPublication = totalPublication;
    }
}
