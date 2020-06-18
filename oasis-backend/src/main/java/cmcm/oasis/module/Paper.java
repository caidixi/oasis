package cmcm.oasis.module;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Paper {
    @Id
    private String DOI;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String documentTitle;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String authors;

    @OneToMany(mappedBy  = "paper",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Author__Paper> author__paperList;

    @OneToMany(mappedBy  = "paper",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Institution__Paper> institution__Paper;

    @OneToMany(mappedBy  = "paper",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Keyword__Paper> keyword__papers;

    @Lob
    @Column( nullable = false, columnDefinition = "text character set utf8")
    private String authorAffiliations;

    @Column( nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String authorIDs;

    @Column( name = "referencePaper",nullable = false, columnDefinition = "text character set utf8")
    private String references;

    @Column( nullable = false, columnDefinition = "text character set utf8")
    private String referencesArticleNumber;

    @Column( nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String articleNumber;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String dateAddedToXplore;

    @Column
    private int publicationYear;
    
    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String volume;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String issue;

    @Column(nullable = false)
    private String startPage;

    @Column(nullable = false)
    private String endPage;

    @Lob
    @Column(name = "abstract", nullable = false, columnDefinition = "text character set utf8")
    private String paperAbstract;

    @Column(nullable = false)
    private String ISSN;

    @Column(nullable = false)
    private String ISBNs;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String fundingInformation;

    @Column(nullable = false)
    private String PDFLink;

    @Lob
    @Column( nullable = false, columnDefinition = "text character set utf8")
    private String authorKeywords;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String IEEETerms;

    @Column(nullable = false, columnDefinition = "text character set utf8")
    private String INSPECControlledTerms;

    @Lob
    @Column( nullable = false, columnDefinition = "text character set utf8")
    private String INSPECNonControlledTerms;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String mesh_Terms;

    @Column
    private int articleCitationCount;

    @Column
    private int referenceCount;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private String onlineDate;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = false)
    private String meetingDate;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String publisher;

    @Column(nullable = false, columnDefinition = "varchar(255) character set utf8")
    private String documentIdentifier;

    public Paper() {
    }

    public Paper(String documentTitle) {
        this.documentTitle = documentTitle;
        this.authorIDs = "";
        this.references = "";
        this.referencesArticleNumber = "";
        this.articleNumber = "";
        author__paperList = new ArrayList<>();
        institution__Paper = new ArrayList<>();
        keyword__papers = new ArrayList<>();
    }


    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setAuthorAffiliations(String authorAffiliations) {
        this.authorAffiliations = authorAffiliations;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public void setDateAddedToXplore(String dateAddedToXplore) {
        this.dateAddedToXplore = dateAddedToXplore;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public void setISBNs(String ISBNs) {
        this.ISBNs = ISBNs;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    public void setFundingInformation(String fundingInformation) {
        this.fundingInformation = fundingInformation;
    }

    public void setPDFLink(String PDFLink) {
        this.PDFLink = PDFLink;
    }

    public void setAuthorKeywords(String authorKeywords) {
        this.authorKeywords = authorKeywords;
    }

    public void setIEEETerms(String IEEETerms) {
        this.IEEETerms = IEEETerms;
    }

    public void setINSPECControlledTerms(String INSPECControlledTerms) {
        this.INSPECControlledTerms = INSPECControlledTerms;
    }

    public void setINSPECNonControlledTerms(String INSPECNonControlledTerms) {
        this.INSPECNonControlledTerms = INSPECNonControlledTerms;
    }

    public void setMesh_Terms(String mesh_Terms) {
        this.mesh_Terms = mesh_Terms;
    }

    public void setArticleCitationCount(int articleCitationCount) {
        this.articleCitationCount = articleCitationCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDocumentIdentifier(String documentIdentifier) {
        this.documentIdentifier = documentIdentifier;
    }

    public void setAuthorIDs(String authorIDs) {
        this.authorIDs = authorIDs;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public void setReferencesArticleNumber(String referencesArticleNumber) {
        this.referencesArticleNumber = referencesArticleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public String getAuthorAffiliations() {
        return authorAffiliations;
    }

    public Conference getConference() {
        return conference;
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

    public String getDOI() {
        return DOI;
    }

    public String getFundingInformation() {
        return fundingInformation;
    }

    public String getPDFLink() {
        return PDFLink;
    }

    public String getAuthorKeywords() {
        return authorKeywords;
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

    public String getAuthorIDs() {
        return authorIDs;
    }

    public String getReferences() {
        return references;
    }

    public String getReferencesArticleNumber() {
        return referencesArticleNumber;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public List<Author__Paper> getAuthor__paperList() {
        return author__paperList;
    }

    public List<Institution__Paper> getInstitution__Paper() {
        return institution__Paper;
    }

    public List<Keyword__Paper> getKeyword__papers() {
        return keyword__papers;
    }


}
