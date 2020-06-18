package cmcm.oasis.response.body.paper;

import cmcm.oasis.module.*;
import cmcm.oasis.response.body.NameAndId;

import java.io.Serializable;
import java.util.List;

public class PaperDetail implements Serializable {
    private String DOI;

    private String documentTitle;

    private List<NameAndId> authors;

    private List<NameAndId> authorAffiliations;

    private List<NameAndId> keywords;

    private NameAndId publicationTitle;

    private String dateAddedToXplore;

    private int publicationYear;

    private String volume;

    private String issue;

    private String startPage;

    private String endPage;

    private String paperAbstract;

    private String ISSN;

    private String ISBNs;

    private String fundingInformation;

    private String PDFLink;

    private String IEEETerms;

    private String INSPECControlledTerms;

    private String INSPECNonControlledTerms;

    private String mesh_Terms;

    private int articleCitationCount;

    private int referenceCount;

    private String license;

    private String onlineDate;

    private String issueDate;

    private String meetingDate;

    private String publisher;

    private String documentIdentifier;

    public PaperDetail(Paper paper) {
        this.DOI = paper.getDOI();
        this.documentTitle = paper.getDocumentTitle();
        this.dateAddedToXplore = paper.getDateAddedToXplore();
        this.publicationYear = paper.getPublicationYear();
        this.volume = paper.getVolume();
        this.issue = paper.getIssue();
        this.startPage = paper.getStartPage();
        this.endPage = paper.getEndPage();
        this.paperAbstract = paper.getPaperAbstract();
        this.ISSN = paper.getISSN();
        this.ISBNs = paper.getISBNs();
        this.fundingInformation = paper.getFundingInformation();
        this.PDFLink = paper.getPDFLink();
        this.IEEETerms = paper.getIEEETerms();
        this.INSPECControlledTerms = paper.getINSPECControlledTerms();
        this.INSPECNonControlledTerms = paper.getINSPECNonControlledTerms();
        this.mesh_Terms = paper.getMesh_Terms();
        this.articleCitationCount = paper.getArticleCitationCount();
        this.referenceCount = paper.getReferenceCount();
        this.license = paper.getLicense();
        this.onlineDate = paper.getOnlineDate();
        this.issueDate = paper.getIssueDate();
        this.meetingDate = paper.getMeetingDate();
        this.publisher = paper.getPublisher();
        this.documentIdentifier = paper.getDocumentIdentifier();

        /*
        Conference conference = paper.getConference();
        publicationTitle = new NameAndId(conference.getName(),conference.getConferenceId());
        authorInfos = new LinkedList<>();
        authorAffiliations = new LinkedList<>();
        keywords = new LinkedList<>();
        List<Author__Paper> author__papers = paper.getAuthor__paperList();
        List<Institution__Paper> institution__papers = paper.getInstitution__Paper();
        List<Keyword__Paper> keyword__papers = paper.getKeyword__papers();

        for (Author__Paper author__paper:author__papers){
            AuthorInfo author = author__paper.getAuthor();
            authorInfos.add(new NameAndId(author.getName(),author.getAuthorId()+""));
        }
        for (Institution__Paper institution__paper:institution__papers){
            Institution institution = institution__paper.getInstitution();
            authorAffiliations.add(new NameAndId(institution.getName(),institution.getInstitutionId()+""));
        }
        for(Keyword__Paper keyword__paper:keyword__papers){
            Keyword keyword = keyword__paper.getKeyword();
            keywords.add(new NameAndId(keyword.getWord(),keyword.getKeywordId()+""));
        }
        */
    }

    public PaperDetail() {
    }

    public String getDOI() {
        return DOI;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public List<NameAndId> getAuthors() {
        return authors;
    }

    public List<NameAndId> getAuthorAffiliations() {
        return authorAffiliations;
    }

    public List<NameAndId> getKeywords() {
        return keywords;
    }

    public String getDateAddedToXplore() {
        return dateAddedToXplore;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getVolume() {
        return volume;
    }

    public String getIssue() {
        return issue;
    }

    public String getStartPage() {
        return startPage;
    }

    public String getEndPage() {
        return endPage;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public String getISSN() {
        return ISSN;
    }

    public String getISBNs() {
        return ISBNs;
    }

    public String getFundingInformation() {
        return fundingInformation;
    }

    public String getPDFLink() {
        return PDFLink;
    }


    public String getIEEETerms() {
        return IEEETerms;
    }

    public String getINSPECControlledTerms() {
        return INSPECControlledTerms;
    }

    public String getINSPECNonControlledTerms() {
        return INSPECNonControlledTerms;
    }

    public String getMesh_Terms() {
        return mesh_Terms;
    }

    public int getArticleCitationCount() {
        return articleCitationCount;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public String getLicense() {
        return license;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDocumentIdentifier() {
        return documentIdentifier;
    }

    public NameAndId getPublicationTitle() {
        return publicationTitle;
    }

    public void setAuthors(List<NameAndId> authors) {
        this.authors = authors;
    }

    public void setAuthorAffiliations(List<NameAndId> authorAffiliations) {
        this.authorAffiliations = authorAffiliations;
    }

    public void setKeywords(List<NameAndId> keywords) {
        this.keywords = keywords;
    }

    public void setPublicationTitle(NameAndId publicationTitle) {
        this.publicationTitle = publicationTitle;
    }
}
