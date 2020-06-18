package cmcm.oasis.response.body.researchDirection;

import cmcm.oasis.response.body.NameAndId;
import cmcm.oasis.response.body.paper.PaperMessage;
import cmcm.oasis.response.body.paper.PublicationOfYear;

import java.util.LinkedList;
import java.util.List;

public class ResearchDirectionDetail {
    private String name;
    private double activity;
    private int totalPublication;
    private Long researchDirectionId;
    private List<NameAndId> relatedAuthors;
    private List<NameAndId> relatedInstitution;
    private List<PaperMessage> papers;
    private List<PublicationOfYear> publicationOfYear;

    public ResearchDirectionDetail(String name,Long researchDirectionId) {
        this.name = name;
        this.researchDirectionId = researchDirectionId;
        relatedAuthors = new LinkedList<>();
        relatedInstitution = new LinkedList<>();
        papers = new LinkedList<>();
        publicationOfYear = new LinkedList<>();
    }

    public ResearchDirectionDetail() {
    }


    public void addRelatedAuthor(NameAndId nameAndId){
        relatedAuthors.add(nameAndId);
    }

    public void addRelatedInstitution(NameAndId nameAndId){
        relatedInstitution.add(nameAndId);
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


    public Long getResearchDirectionId() {
        return researchDirectionId;
    }

    public List<NameAndId> getRelatedAuthors() {
        return relatedAuthors;
    }

    public List<NameAndId> getRelatedInstitution() {
        return relatedInstitution;
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

    public int getTotalPublication() {
        return totalPublication;
    }

    public void setTotalPublication(int totalPublication) {
        this.totalPublication = totalPublication;
    }
}
