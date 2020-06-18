package cmcm.oasis.serviceImpl;

import cmcm.oasis.module.*;
import cmcm.oasis.repository.*;
import cmcm.oasis.response.body.InterestingPoints;
import cmcm.oasis.response.Response;
import cmcm.oasis.response.body.NameAndId;
import cmcm.oasis.response.body.author.AuthorDetail;
import cmcm.oasis.response.body.conference.ConferenceDetail;
import cmcm.oasis.response.body.graph.AuthorGraph;
import cmcm.oasis.response.body.graph.AuthorLink;
import cmcm.oasis.response.body.graph.AuthorNode;
import cmcm.oasis.response.body.institution.InstitutionDetail;
import cmcm.oasis.response.body.paper.*;
import cmcm.oasis.response.body.researchDirection.ResearchDirectionDetail;
import cmcm.oasis.response.body.trend.*;
import cmcm.oasis.service.ActivityService;
import cmcm.oasis.service.DataService;
import cmcm.oasis.service.PaperService;

import cmcm.oasis.util.PaperCrawler;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

@Service
public class PaperServiceImpl implements PaperService {
    private PaperRepository paperRepository;
    private ConferenceRepository conferenceRepository;
    private AuthorRepository authorRepository;
    private AuthorPaperRepository authorPaperRepository;
    private InstitutionRepository institutionRepository;
    private InstitutionPaperRepository institutionPaperRepository;
    private KeywordRepository keywordRepository;
    private KeywordPaperRepository keywordPaperRepository;
    private AuthorSideRepository authorSideRepository;
    private ActivityService activityService;
    private DataService dataService;
    private PaperCrawler paperCrawler;

    @Autowired
    public PaperServiceImpl(PaperRepository paperRepository, ConferenceRepository conferenceRepository, AuthorRepository authorRepository,
                            AuthorPaperRepository authorPaperRepository, InstitutionRepository institutionRepository,
                            InstitutionPaperRepository institutionPaperRepository, KeywordRepository keywordRepository,AuthorSideRepository authorSideRepository,
                            KeywordPaperRepository keywordPaperRepository, ActivityService activityService,DataService dataService, PaperCrawler paperCrawler) {
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.authorRepository = authorRepository;
        this.authorPaperRepository = authorPaperRepository;
        this.institutionRepository = institutionRepository;
        this.institutionPaperRepository = institutionPaperRepository;
        this.keywordRepository = keywordRepository;
        this.keywordPaperRepository = keywordPaperRepository;
        this.authorSideRepository = authorSideRepository;
        this.activityService = activityService;
        this.dataService = dataService;
        this.paperCrawler = paperCrawler;
    }

    @Override
    public Response crawlData(String conference, int start, int end) {
        String url = "https://ieeexplore.ieee.org/rest/search";

        Map<String, String> headers = new HashMap<>();
        headers.put("HOST", "ieeexplore.ieee.org");
        headers.put("Origin", "https://ieeexplore.ieee.org");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contentType", "conferences");
        jsonObject.put("queryText", conference);
        jsonObject.put("rowsPerPage", "10");
        jsonObject.put("pageNumber", "1");

        JSONObject json = paperCrawler.Post(url, headers, jsonObject);
        System.out.println(json.toString());
        JSONArray records = (JSONArray) json.get("records");
        for (Object o : records) {
            System.out.println("get record!："+JSON.toJSONString(o));
            JSONObject j = (JSONObject) o;
            int publicationYear = Integer.parseInt((String) j.get("publicationYear"));
            if (publicationYear >= start && publicationYear <= end && j.get("authors") != null) {     //填充文献信息，若信息不全可以从输出的JSON数据中添加
                Paper paper = new Paper((String) j.get("articleTitle"));
                StringBuilder authorsNames = new StringBuilder();
                StringBuilder authorAffiliation = new StringBuilder();
                for (Object ao : (JSONArray) j.get("authors")) {
                    JSONObject ja = (JSONObject) ao;
                    if (authorsNames.toString().equals("")) {
                        authorsNames = new StringBuilder((String) ja.get("searchablePreferredName"));
                        if (ja.get("id") != null) {
                            authorAffiliation = new StringBuilder(paperCrawler.getInstitute(String.valueOf(ja.get("id"))));
                        }
                    } else {
                        authorsNames.append("; ").append((String) ja.get("searchablePreferredName"));
                        authorAffiliation.append("; ").append(paperCrawler.getInstitute(String.valueOf(ja.get("id"))));
                    }
                }

                paper.setAuthors(authorsNames.toString());
                paper.setAuthorAffiliations(authorAffiliation.toString());
                paper.setDateAddedToXplore("");
                paper.setPublicationYear(Integer.parseInt((String) j.get("publicationYear")));
                paper.setVolume("");
                paper.setIssue("");
                paper.setStartPage((String) j.get("startPage"));
                //paper.setEndPage((String) j.get("endPage"));
                paper.setEndPage("");
                paper.setPaperAbstract((String) j.get("abstract"));
                paper.setISSN("");
                paper.setISBNs("");
                paper.setDOI((String) j.get("doi"));
                paper.setFundingInformation("");
                paper.setPDFLink((String) j.get("pdfLink"));
                paper.setAuthorKeywords(paperCrawler.getKeywords((String) j.get("articleNumber")));
                paper.setIEEETerms("");
                paper.setINSPECControlledTerms("");
                paper.setINSPECNonControlledTerms("");
                paper.setMesh_Terms("");
                paper.setArticleCitationCount((int) j.get("citationCount"));
                paper.setReferenceCount(paperCrawler.getReference((String) j.get("articleNumber")));
                paper.setLicense("");
                paper.setOnlineDate("");
                paper.setIssueDate("");
                paper.setMeetingDate("");
                paper.setPublisher("IEEE");
                paper.setDocumentIdentifier((String) j.get("docIdentifier"));


                String publicationTitle = (String) j.get("publicationTitle");
                dataService.inputData(paper,publicationTitle);
            }
        }
        //更新活跃度
        activityService.updateActivity();
        return new Response(0, "Success");
    }

    @Override
    public Response LoadData(MultipartFile file) {
        try {
            //更新数据
            dataService.updateData(file);
            return new Response(0, "Success");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(1, "Upload fail");
        }
    }

    @Override
    public Response queryInterval() {
        Paper startYearPaper = paperRepository.findTop1ByOrderByPublicationYearAsc();
        Paper endYearPaper = paperRepository.findTop1ByOrderByPublicationYearDesc();
        if (startYearPaper != null && endYearPaper != null) {
            int startYear = startYearPaper.getPublicationYear();
            int endYear = endYearPaper.getPublicationYear();
            return new Response(0, "success", new Interval(startYear, endYear));
        } else {
            return new Response(0, "not found");
        }
    }

    @Override
    public Response queryPaper(String authorName, String institute, String conference, String keyword, int startYear, int endYear) {
        List<Paper> papers = paperRepository.findByAuthorsContainsAndAuthorAffiliationsContainsAndConference_NameContainsAndAuthorKeywordsContainsAndPublicationYearGreaterThanEqualAndPublicationYearLessThanEqual(authorName, institute, conference, keyword, startYear, endYear);
        List<PaperShortcut> paperShortcuts = new ArrayList<>();
        for (Paper paper : papers) {
            PaperShortcut paperShortcut = new PaperShortcut(paper);

            List<NameAndId> authors = new LinkedList<>();
            List<NameAndId> authorAffiliations = new LinkedList<>();
            List<Author__Paper> author__papers = paper.getAuthor__paperList();
            List<Institution__Paper> institution__papers = paper.getInstitution__Paper();
            for (Author__Paper author__paper : author__papers) {
                Author author = author__paper.getAuthor();
                AuthorActivity authorActivity = activityService.getAuthorActivity(author.getAuthorId());
                authors.add(new NameAndId(author.getName(), author.getAuthorId() + "", authorActivity.getActivity()));
            }
            for (Institution__Paper institution__paper : institution__papers) {
                Institution institution = institution__paper.getInstitution();
                InstitutionActivity institutionActivity = activityService.getInstitutionActivity(institution.getInstitutionId());
                authorAffiliations.add(new NameAndId(institution.getName(), institution.getInstitutionId() + "", institutionActivity.getActivity()));
            }

            paperShortcut.setAuthors(authors);
            paperShortcut.setAuthorAffiliations(authorAffiliations);
            paperShortcuts.add(paperShortcut);
        }
        return new Response(0, "success", paperShortcuts);
    }

    @Override
    public Response queryPaperDetail(String DOI) {
        Paper paper = paperRepository.findByDOI(DOI);

        if (paper != null) {
            PaperDetail paperDetail = new PaperDetail(paper);

            Conference conference = paper.getConference();
            NameAndId publicationTitle = new NameAndId(conference.getName(), conference.getConferenceId());
            paperDetail.setPublicationTitle(publicationTitle);

            List<NameAndId> authors = new LinkedList<>();
            List<NameAndId> authorAffiliations = new LinkedList<>();
            List<NameAndId> keywords = new LinkedList<>();
            List<Author__Paper> author__papers = paper.getAuthor__paperList();
            List<Institution__Paper> institution__papers = paper.getInstitution__Paper();
            List<Keyword__Paper> keyword__papers = paper.getKeyword__papers();

            for (Author__Paper author__paper : author__papers) {
                Author author = author__paper.getAuthor();
                AuthorActivity authorActivity = activityService.getAuthorActivity(author.getAuthorId());
                authors.add(new NameAndId(author.getName(), author.getAuthorId() + "",authorActivity.getActivity() ));
            }
            paperDetail.setAuthors(authors);

            for (Institution__Paper institution__paper : institution__papers) {
                Institution institution = institution__paper.getInstitution();
                InstitutionActivity institutionActivity = activityService.getInstitutionActivity(institution.getInstitutionId());
                authorAffiliations.add(new NameAndId(institution.getName(), institution.getInstitutionId() + "",institutionActivity.getActivity()));
            }
            paperDetail.setAuthorAffiliations(authorAffiliations);

            for (Keyword__Paper keyword__paper : keyword__papers) {
                Keyword keyword = keyword__paper.getKeyword();
                KeywordActivity keywordActivity = activityService.getKeywordActivity(keyword.getKeywordId());
                keywords.add(new NameAndId(keyword.getWord(), keyword.getKeywordId() + "", keywordActivity.getActivity()));
            }
            paperDetail.setKeywords(keywords);

            return new Response(0, "success", paperDetail);
        } else {
            return new Response(404, "not found");
        }
    }

    @Override
    public Response queryAuthorDetail(Long authorId) {
        Author author = authorRepository.findAuthorByAuthorId(authorId);
        if (author != null) {
            Institution institution = author.getInstitution();
            AuthorDetail authorDetail = new AuthorDetail(author.getName(), authorId, institution.getName(), institution.getInstitutionId());

            //查询发文量、引用数、活跃度、h指数、g指数、hg指数
            AuthorActivity authorActivity = activityService.getAuthorActivity(authorId);
            authorDetail.setTotalPublication(authorActivity.getPublication());
            authorDetail.setTotalReferenceCount(authorActivity.getCitation());
            authorDetail.setActivity(authorActivity.getActivity());
            authorDetail.setHindex(authorActivity.getHindex());
            authorDetail.setGindex(authorActivity.getGindex());
            authorDetail.setHgindex(authorActivity.getHgindex());

            //查找研究领域
            /*
            List<Object[]> keywordCounts = keywordPaperRepository.findFieldOfResearchByAuthorId(authorId);
            for (Object[] keywordCount :keywordCounts){
                Keyword keyword = keywordRepository.findKeywordByKeywordId(Long.parseLong(String.valueOf(keywordCount[0])));
                authorDetail.addFieldOfResearch(new NameAndId(keyword.getWord(),keyword.getKeywordId()));
            }
            */
            authorDetail.addFieldOfResearch(findFieldOfResearch(authorId, "author"));

            //查找发表论文
            List<Author__Paper> author__papers = authorPaperRepository.findByAuthorAuthorIdOrderByReferenceDesc(authorId);
            HashSet<String> dois = new HashSet<>();
            for (Author__Paper author__paper : author__papers) {
                dois.add(author__paper.getPaper().getDOI());
            }
            List<Paper> papers = paperRepository.findByDOIInOrderByReferenceCountDesc(dois);
            for (Paper paper : papers) {
                authorDetail.addPaper(new PaperMessage(paper.getDOI(), paper.getDocumentTitle(), paper.getAuthors(), paper.getReferenceCount(), paper.getPublicationYear()));
            }

            //查找历年发表论文
            /*
            for(int year = 2020;year>1980;year--){
                int publicationOfYear = paperRepository.countByPublicationYearAndDOIIn(year,dois);
                if (publicationOfYear!=0){
                    authorDetail.addPublicationOfYear(new PublicationOfYear(year,publicationOfYear));
                }
            }
            */
            authorDetail.addPublicationOfYear(findPublicationOfYear(1980, 2020, dois));
            return new Response(0, "success", authorDetail);
        } else {
            return new Response(404, "not found");
        }
    }

    @Override
    public Response queryInstituteDetail(Long institutionId) {
        Institution institution = institutionRepository.findInstitutionByInstitutionId(institutionId);
        if (institution != null) {
            InstitutionDetail institutionDetail = new InstitutionDetail(institution.getName(), institutionId);

            //查询发文量、引用数、活跃度、h指数、g指数、hg指数
            InstitutionActivity institutionActivity = activityService.getInstitutionActivity(institutionId);
            institutionDetail.setTotalPublication(institutionActivity.getPublication());
            institutionDetail.setTotalReferenceCount(institutionActivity.getCitation());
            institutionDetail.setActivity(institutionActivity.getActivity());
            institutionDetail.setHindex(institutionActivity.getHindex());
            institutionDetail.setGindex(institutionActivity.getGindex());
            institutionDetail.setHgindex(institutionActivity.getHgindex());

            //关联学者
            List<Author> authors = authorRepository.findByInstitution(institution);
            for (Author author : authors) {
                AuthorActivity authorActivity = activityService.getAuthorActivity(author.getAuthorId());
                //int publicationCount = authorPaperRepository.countByAuthor(author);
                //int citationCount =  Integer.parseInt(String.valueOf(paperRepository.findReferenceCountByAuthorId(author.getAuthorId()).get(0)[0]));
                institutionDetail.addRelatedAuthor(new NameAndId(author.getName(), author.getAuthorId(), authorActivity.getActivity(),authorActivity.getPublication(),authorActivity.getCitation()));
            }
            //研究领域
            institutionDetail.addFieldOfResearch(findFieldOfResearch(institutionId, "institution"));
            //查找发表论文
            List<Institution__Paper> institution__papers = institutionPaperRepository.findByInstitution(institution);
            HashSet<String> dois = new HashSet<>();
            for (Institution__Paper institution__paper : institution__papers) {
                dois.add(institution__paper.getPaper().getDOI());
            }
            List<Paper> papers = paperRepository.findByDOIInOrderByReferenceCountDesc(dois);
            for (Paper paper : papers) {
                institutionDetail.addPaper(new PaperMessage(paper.getDOI(), paper.getDocumentTitle(), paper.getAuthors(), paper.getReferenceCount(), paper.getPublicationYear()));
            }
            //历年论文发表量
            /*
            for(int year = 2020;year>1980;year--){
                int publicationOfYear = paperRepository.countByPublicationYearAndDOIIn(year,dois);
                if (publicationOfYear!=0){
                    institutionDetail.addPublicationOfYear(new PublicationOfYear(year,publicationOfYear));
                }
            }
            */
            institutionDetail.addPublicationOfYear(findPublicationOfYear(1981, 2021, dois));

            return new Response(0, "success", institutionDetail);
        } else {
            return new Response(404, "not found");
        }
    }

    @Override
    public Response queryConferenceDetail(Long conferenceId) {
        Conference conference = conferenceRepository.findConferenceByConferenceId(conferenceId);
        if (conference != null) {
            ConferenceDetail conferenceDetail = new ConferenceDetail(conference.getName(), conferenceId);
            conferenceDetail.addFieldOfResearch(findFieldOfResearch(conferenceId, "conference"));
            List<Paper> papers = paperRepository.findByConferenceOrderByReferenceCountDesc(conference);
            for (Paper paper : papers) {
                conferenceDetail.addPaper(new PaperMessage(paper.getDOI(), paper.getDocumentTitle(), paper.getAuthors(), paper.getReferenceCount(), paper.getPublicationYear()));
            }
            return new Response(0, "success", conferenceDetail);
        } else {
            return new Response(404, "not found");
        }
    }

    @Override
    public Response queryResearchDirectionDetail(Long researchDirectionId) {
        Keyword keyword = keywordRepository.findKeywordByKeywordId(researchDirectionId);
        if (keyword != null) {
            ResearchDirectionDetail researchDirectionDetail = new ResearchDirectionDetail(keyword.getWord(), researchDirectionId);

            //发文量、活跃度
            KeywordActivity keywordActivity = activityService.getKeywordActivity(keyword.getKeywordId());
            researchDirectionDetail.setTotalPublication(keywordActivity.getPublication());
            researchDirectionDetail.setActivity(keywordActivity.getActivity());

            //关联学者
            List<Object[]> authorCounts = authorPaperRepository.findRelatedAuthorsByKeywordId(researchDirectionId);
            for (Object[] authorCount : authorCounts) {
                Author author = authorRepository.findAuthorByAuthorId(Long.parseLong(String.valueOf(authorCount[0])));
                AuthorActivity authorActivity = activityService.getAuthorActivity(author.getAuthorId());
                researchDirectionDetail.addRelatedAuthor(new NameAndId(author.getName(), author.getAuthorId(), authorActivity.getActivity(), Integer.parseInt(String.valueOf(authorCount[1])),authorActivity.getCitation()));
            }
            //关联机构
            List<Object[]> institutionCounts = institutionPaperRepository.findRelatedInstitutionsByKeywordId(researchDirectionId);
            for (Object[] institutionCount : institutionCounts) {
                Institution institution = institutionRepository.findInstitutionByInstitutionId(Long.parseLong(String.valueOf(institutionCount[0])));
                InstitutionActivity institutionActivity = activityService.getInstitutionActivity(institution.getInstitutionId());
                //int citationNum = Integer.parseInt(String.valueOf(keywordPaperRepository.countReferenceByKeywordAndInstitution(keyword.getKeywordId(),institution.getInstitutionId()).get(0)[0]));
                researchDirectionDetail.addRelatedInstitution(new NameAndId(institution.getName(), institution.getInstitutionId(), institutionActivity.getActivity(), Integer.parseInt(String.valueOf(institutionCount[1])),institutionActivity.getCitation()));
            }
            //发表论文
            List<Keyword__Paper> keyword__papers = keywordPaperRepository.findByKeyword(keyword);
            HashSet<String> dois = new HashSet<>();
            for (Keyword__Paper keyword__paper : keyword__papers) {
                dois.add(keyword__paper.getPaper().getDOI());
            }
            List<Paper> papers = paperRepository.findByDOIInOrderByReferenceCountDesc(dois);
            for (Paper paper : papers) {
                researchDirectionDetail.addPaper(new PaperMessage(paper.getDOI(), paper.getDocumentTitle(), paper.getAuthors(), paper.getReferenceCount(), paper.getPublicationYear()));
            }

            researchDirectionDetail.addPublicationOfYear(findPublicationOfYear(1981, 2021, dois));
            return new Response(0, "success", researchDirectionDetail);
        } else {
            return new Response(404, "not found");
        }
    }

    @Override
    public Response getPaperTrend(String sort) {
        LinkedList<PaperTrend> trend= activityService.getPaperTrend(sort);
        return new Response(0, "success", trend);
    }

    @Override
    public Response getAuthorTrend(String sort) {
        LinkedList<AuthorTrend> trend = activityService.getAuthorTrend(sort);
        return new Response(0, "success", trend);
    }

    @Override
    public Response getInstituteTrend(String sort) {
        LinkedList<InstitutionTrend> trend = activityService.getInstitutionTrend(sort);
        return new Response(0, "success", trend);
    }

    @Override
    public Response getResearchHotSpots(String sort) {
        LinkedList<KeywordTrend> trend = activityService.getKeywordTrend(sort);
        return new Response(0, "success", trend);
    }

    @Override
    public Response getInterestingPoints() {
        List<Paper> papersByReference = paperRepository.findTop3ByOrderByReferenceCountDesc();
        return new Response(0, "success", new InterestingPoints(papersByReference));
    }

    @Override
    public Response getSegmentation(String type, double percentage) {
        switch (type) {
            case "author": {
                return new Response(0, "success", activityService.getAuthorSegmentation(percentage));
            }
            case "institution": {
                return new Response(0, "success", activityService.getInstitutionSegmentation(percentage));
            }
            case "keyword": {
                return new Response(0, "success", activityService.getKeywordSegmentation(percentage));
            }
        }
        return new Response(1, "no type match");
    }


    @Override
    public Response getMainAuthorGraph() {
        List<AuthorSide> authorSides = authorSideRepository.findTop1000ByOrderByWeightDesc();
        AuthorGraph authorGraph = new AuthorGraph();
        HashSet<Author> authorHashSet = new HashSet<>();
        for(AuthorSide authorSide:authorSides){
            Author source = authorSide.getSource();
            Author target = authorSide.getTarget();
            if (!authorHashSet.contains(source)){
                AuthorActivity authorActivity = activityService.getAuthorActivity(source.getAuthorId());
                authorGraph.addNodes(new AuthorNode(source.getAuthorId(),source.getName(),source.getInstitution().getName(),authorActivity.getActivity()));
                authorHashSet.add(source);
            }
            if (!authorHashSet.contains(target)){
                AuthorActivity authorActivity = activityService.getAuthorActivity(target.getAuthorId());
                authorGraph.addNodes(new AuthorNode(target.getAuthorId(),target.getName(),target.getInstitution().getName(),authorActivity.getActivity()));
                authorHashSet.add(target);
            }
            authorGraph.addLinks(new AuthorLink(source.getAuthorId(),target.getAuthorId(),authorSide.getWeight()));
        }
        return new Response(0, "success",authorGraph);
    }

    private LinkedList<NameAndId> findFieldOfResearch(Long id, String type) {
        LinkedList<NameAndId> result = new LinkedList<>();
        List<Object[]> keywordCounts;
        switch (type) {
            case "author": {
                keywordCounts = keywordPaperRepository.findFieldOfResearchByAuthorId(id);
                for (Object[] keywordCount : keywordCounts) {
                    Keyword keyword = keywordRepository.findKeywordByKeywordId(Long.parseLong(String.valueOf(keywordCount[0])));
                    KeywordActivity keywordActivity = activityService.getKeywordActivity(keyword.getKeywordId());
                    List<Author__Paper> author__papers = authorPaperRepository.findByAuthorAuthorIdOrderByReferenceDesc(id);
                    HashSet<Paper> papers = new HashSet<>();
                    for (Author__Paper author__paper : author__papers) {
                        papers.add(author__paper.getPaper());
                    }
                    int paperNum = keywordPaperRepository.countByKeywordAndPaperIn(keyword, papers);
                    int citationNum = Integer.parseInt(String.valueOf(keywordPaperRepository.countReferenceByKeywordAndAuthor(keyword.getKeywordId(),id).get(0)[0]));
                    result.addLast(new NameAndId(keyword.getWord(), keyword.getKeywordId(), keywordActivity.getActivity(), paperNum,citationNum));
                }
                break;
            }
            case "institution": {
                keywordCounts = keywordPaperRepository.findFieldOfResearchByInstitutionId(id);
                for (Object[] keywordCount : keywordCounts) {
                    Keyword keyword = keywordRepository.findKeywordByKeywordId(Long.parseLong(String.valueOf(keywordCount[0])));
                    KeywordActivity keywordActivity = activityService.getKeywordActivity(keyword.getKeywordId());
                    List<Institution__Paper> institution__papers = institutionPaperRepository.findByInstitutionInstitutionId(id);
                    HashSet<Paper> papers = new HashSet<>();
                    for (Institution__Paper institution__paper : institution__papers) {
                        papers.add(institution__paper.getPaper());
                    }
                    int paperNum = keywordPaperRepository.countByKeywordAndPaperIn(keyword, papers);
                    int citationNum = Integer.parseInt(String.valueOf(keywordPaperRepository.countReferenceByKeywordAndInstitution(keyword.getKeywordId(),id).get(0)[0]));
                    result.addLast(new NameAndId(keyword.getWord(), keyword.getKeywordId(), keywordActivity.getActivity(), paperNum,citationNum));
                }
                break;
            }
            case "conference": {
                keywordCounts = keywordPaperRepository.findFieldOfResearchByConferenceId(id);
                for (Object[] keywordCount : keywordCounts) {
                    Keyword keyword = keywordRepository.findKeywordByKeywordId(Long.parseLong(String.valueOf(keywordCount[0])));
                    KeywordActivity keywordActivity = activityService.getKeywordActivity(keyword.getKeywordId());
                    List<Paper> papers = paperRepository.findByConferenceConferenceId(id);
                    int paperNum = keywordPaperRepository.countByKeywordAndPaperIn(keyword, papers);
                    result.addLast(new NameAndId(keyword.getWord(), keyword.getKeywordId(), keywordActivity.getActivity(), paperNum));
                }
                break;
            }
        }
        return result;
    }

    private LinkedList<PublicationOfYear> findPublicationOfYear(int beginYear, int endYear, HashSet<String> dois) {
        LinkedList<PublicationOfYear> result = new LinkedList<>();
        for (int year = endYear; year >= beginYear; year--) {
            int publicationOfYear = paperRepository.countByPublicationYearAndDOIIn(year, dois);
            if (publicationOfYear != 0) {
                result.addLast(new PublicationOfYear(year, publicationOfYear));
            }
        }
        return result;
    }
}
