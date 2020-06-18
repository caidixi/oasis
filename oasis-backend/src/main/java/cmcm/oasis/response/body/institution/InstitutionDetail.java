package cmcm.oasis.response.body.institution;

import cmcm.oasis.response.body.NameAndId;
import cmcm.oasis.response.body.paper.PaperMessage;
import cmcm.oasis.response.body.paper.PublicationOfYear;

import java.util.LinkedList;
import java.util.List;

public class InstitutionDetail {
    private String name;

    private Long institutionId;

    private int totalReferenceCount;

    private int totalPublication;

    private double activity;

    private int hindex;

    private int gindex;

    private double hgindex;

    private List<NameAndId> relatedAuthors;

    private List<NameAndId> fieldOfResearches;

    private List<PaperMessage> papers;

    private List<PublicationOfYear> publicationOfYear;

    public InstitutionDetail() {
    }

    public InstitutionDetail(String name, Long institutionId) {
        this.name = name;
        this.institutionId = institutionId;
        relatedAuthors = new LinkedList<>();
        fieldOfResearches = new LinkedList<>();
        papers = new LinkedList<>();
        publicationOfYear = new LinkedList<>();
    }

    public void addRelatedAuthor(NameAndId relatedAuthor){
        relatedAuthors.add(relatedAuthor);
    }

    public void addFieldOfResearch(List<NameAndId> fieldOfResearch){
        fieldOfResearches.addAll(fieldOfResearch);
    }

    public void addPaper(PaperMessage paperMessage){
        papers.add(paperMessage);
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

    public Long getInstitutionId() {
        return institutionId;
    }

    public List<NameAndId> getRelatedAuthors() {
        return relatedAuthors;
    }

    public List<NameAndId> getFieldOfResearch() {
        return fieldOfResearches;
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

    public int getTotalReferenceCount() {
        return totalReferenceCount;
    }

    public void setTotalReferenceCount(int totalReferenceCount) {
        this.totalReferenceCount = totalReferenceCount;
    }

    public int getTotalPublication() {
        return totalPublication;
    }

    public void setTotalPublication(int totalPublication) {
        this.totalPublication = totalPublication;
    }
}
