package cmcm.oasis.serviceImpl;

import cmcm.oasis.module.*;
import cmcm.oasis.repository.*;
import cmcm.oasis.service.ActivityService;
import cmcm.oasis.service.DataService;
import com.csvreader.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    private PaperRepository paperRepository;
    private ConferenceRepository conferenceRepository;
    private AuthorRepository authorRepository;
    private AuthorPaperRepository authorPaperRepository;
    private InstitutionRepository institutionRepository;
    private InstitutionPaperRepository institutionPaperRepository;
    private KeywordRepository keywordRepository;
    private KeywordPaperRepository keywordPaperRepository;
    private ActivityService activityService;
    private AuthorSideRepository authorSideRepository;

    @Autowired
    public DataServiceImpl(PaperRepository paperRepository, ConferenceRepository conferenceRepository, AuthorRepository authorRepository,
                           AuthorPaperRepository authorPaperRepository, InstitutionRepository institutionRepository,
                           InstitutionPaperRepository institutionPaperRepository, KeywordRepository keywordRepository,
                           KeywordPaperRepository keywordPaperRepository,ActivityService activityService,AuthorSideRepository authorSideRepository) {
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.authorRepository = authorRepository;
        this.authorPaperRepository = authorPaperRepository;
        this.institutionRepository = institutionRepository;
        this.institutionPaperRepository = institutionPaperRepository;
        this.keywordRepository = keywordRepository;
        this.keywordPaperRepository = keywordPaperRepository;
        this.activityService = activityService;
        this.authorSideRepository = authorSideRepository;
    }

    @Override
    @Async
    public void updateData(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            CsvReader csvReader = new CsvReader(inputStream, Charset.forName("UTF-8"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String documentTitle = csvReader.get("Document Title");
                String doi = csvReader.get("DOI");
                String authorsNames = csvReader.get("Authors");
                String authorAffiliation = csvReader.get("Author Affiliations");
                String keywords = csvReader.get("Author Keywords");
                String conferenceName = csvReader.get("Publication Title");

                //判断是否为合理的数据
                if (!isCorrectPaper(doi, documentTitle, authorsNames, authorAffiliation, keywords,conferenceName)) {
                    continue;
                }

                Paper paper = new Paper(csvReader.get("Document Title"));
                paper.setAuthors(authorsNames);
                paper.setAuthorAffiliations(authorAffiliation);
                paper.setDateAddedToXplore(csvReader.get("Date Added To Xplore"));
                if (!csvReader.get("Publication Year").equals("")) {
                    paper.setPublicationYear(Integer.parseInt(csvReader.get("Publication Year")));
                }
                paper.setVolume(csvReader.get("Volume"));
                paper.setIssue(csvReader.get("Issue"));
                paper.setStartPage(csvReader.get("Start Page"));
                paper.setEndPage(csvReader.get("End Page"));
                paper.setPaperAbstract(csvReader.get("Abstract"));
                paper.setISSN(csvReader.get("ISSN"));
                paper.setISBNs(csvReader.get("ISBNs"));
                paper.setDOI(csvReader.get("DOI"));
                paper.setFundingInformation(csvReader.get("Funding Information"));
                paper.setPDFLink(csvReader.get("PDF Link"));
                paper.setAuthorKeywords(csvReader.get("Author Keywords"));
                paper.setIEEETerms(csvReader.get("IEEE Terms"));
                paper.setINSPECControlledTerms(csvReader.get("INSPEC Controlled Terms"));

                paper.setINSPECNonControlledTerms(csvReader.get("INSPEC Non-Controlled Terms"));
                paper.setMesh_Terms(csvReader.get("Mesh_Terms"));

                if (!csvReader.get("Article Citation Count").equals("")) {
                    paper.setArticleCitationCount(Integer.parseInt(csvReader.get("Article Citation Count")));
                }
                if (!csvReader.get("Reference Count").equals("")) {
                    paper.setReferenceCount(Integer.parseInt(csvReader.get("Reference Count")));
                }
                paper.setLicense(csvReader.get("License"));
                paper.setOnlineDate(csvReader.get("Online Date"));
                paper.setIssueDate(csvReader.get("Issue Date"));
                paper.setMeetingDate(csvReader.get("Meeting Date"));
                paper.setPublisher(csvReader.get("Publisher"));
                paper.setDocumentIdentifier(csvReader.get("Document Identifier"));

                if(csvReader.get("Authors ID")!=null&&csvReader.get("References")!=null&&csvReader.get("References Article Number")!=null&&csvReader.get("Article Number")!=null){
                    paper.setAuthorIDs(csvReader.get("Authors ID"));
                    paper.setReferences(csvReader.get("References"));
                    paper.setReferencesArticleNumber(csvReader.get("References Article Number"));
                    paper.setArticleNumber(csvReader.get("Article Number"));
                }

                //录入数据
                inputData(paper,conferenceName);
            }

            //更新活跃度
            //activityService.updateActivity();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void inputData(Paper paper, String conferenceName){
        //录入会议
        Conference conference = conferenceRepository.findByName(conferenceName);
        if (conference == null) {
            conference = conferenceRepository.saveAndFlush(new Conference(conferenceName));
        }
        paper.setConference(conference);
        paperRepository.saveAndFlush(paper);

        //更新论文活跃度
        activityService.updatePaperActivity(paper);

        //录入学者、机构
        String[] authors = paper.getAuthors().split("; ");
        String[] affiliations = paper.getAuthorAffiliations().split("; ");
        String[] authorsIEEEId = paper.getAuthorIDs().split("; ");
        for (int i = 0; i < authors.length; i++) {
            String affiliationName = affiliations[i];
            Institution institution = institutionRepository.findByName(affiliationName);
            if (institution == null) {
                institution = institutionRepository.saveAndFlush(new Institution(affiliationName));
            }
            if (institutionPaperRepository.findByInstitutionAndPaperDOI(institution, paper.getDOI()) == null) {
                Institution__Paper institution__paper = new Institution__Paper(paper, institution,i + 1,paper.getReferenceCount());
                institutionPaperRepository.saveAndFlush(institution__paper);
            }

            String authorName = authors[i];
            Author author = authorRepository.findByNameAndInstitution(authorName,institution);
            if (author == null) {
                author = new Author(authorName, institution);
                if (authors.length==authorsIEEEId.length){
                    author.setIeeeID(authorsIEEEId[i]);
                }
                authorRepository.saveAndFlush(author);
            }
            Author__Paper author__paper = new Author__Paper(paper, author, i + 1,paper.getReferenceCount(),authors.length);
            authorPaperRepository.saveAndFlush(author__paper);

            //更新机构、作者活跃度
            activityService.updateAuthorActivity(author);
            activityService.updateInstitutionActivity(institution);
        }

        //录入关键字
        String[] keywordList = paper.getAuthorKeywords().replace(", ",",").replace(",", ";").split(";");
        for (String word : keywordList) {
            Keyword keyword = keywordRepository.findKeywordByWord(word);
            if (keyword == null) {
                keyword = keywordRepository.saveAndFlush(new Keyword(word));
            }
            Keyword__Paper keyword__paper = new Keyword__Paper(paper, keyword,paper.getReferenceCount());
            keywordPaperRepository.save(keyword__paper);

            //更新关键词活跃度
            activityService.updateKeywordActivity(keyword);
        }

        //录入作者关系
        List<Author__Paper>  author__papers = authorPaperRepository.findByPaper(paper);
        HashSet<Author> authorHashSet = new HashSet<>();
        for(Author__Paper source:author__papers){
            Author sourceAuthor = source.getAuthor();
            authorHashSet.add(sourceAuthor);
            List<Author__Paper> targets = authorPaperRepository.findByPaperAndAuthorNotIn(paper,authorHashSet);
            for(Author__Paper target:targets){
                Author targetAuthor = target.getAuthor();
                AuthorSide authorSide = authorSideRepository.findBySourceAndTarget(sourceAuthor,targetAuthor);
                if (authorSide==null){
                    authorSide = authorSideRepository.findBySourceAndTarget(targetAuthor,sourceAuthor);
                }
                if (authorSide==null){
                    authorSide = new AuthorSide(sourceAuthor,targetAuthor);
                }
                authorSide.addWeight();
                authorSideRepository.saveAndFlush(authorSide);
            }
        }
    }

    private boolean isCorrectPaper(String doi, String documentTitle, String authorsName, String authorAffiliations, String keywords,String conferenceName) {
        //System.out.print(!doi.equals(""));
        //System.out.print(paperRepository.findByDOI(doi) == null);
        //System.out.print(!documentTitle.equals(""));
        //System.out.print(!authorsName.equals(""));
        //System.out.print(!authorAffiliations.equals(""));
        //System.out.print(!keywords.equals(""));
        //System.out.print(!conferenceName.equals(""));
        //System.out.println();
        return !doi.equals("") && paperRepository.findByDOI(doi) == null && !documentTitle.equals("") && !authorsName.equals("") && !authorAffiliations.equals("") && !keywords.equals("")&&!conferenceName.equals("");
    }
}
