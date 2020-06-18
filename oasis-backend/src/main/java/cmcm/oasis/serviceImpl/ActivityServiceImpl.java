package cmcm.oasis.serviceImpl;

import cmcm.oasis.module.*;
import cmcm.oasis.repository.*;
import cmcm.oasis.response.body.trend.*;
import cmcm.oasis.service.ActivityService;
import cmcm.oasis.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private PaperRepository paperRepository;
    private AuthorRepository authorRepository;
    private InstitutionRepository institutionRepository;
    private KeywordRepository keywordRepository;
    private AuthorPaperRepository authorPaperRepository;
    private InstitutionPaperRepository institutionPaperRepository;
    private KeywordPaperRepository keywordPaperRepository;
    private KeywordActivityRepository keywordActivityRepository;
    private AuthorActivityRepository authorActivityRepository;
    private PaperActivityRepository paperActivityRepository;
    private InstitutionActivityRepository institutionActivityRepository;

    @Autowired
    public ActivityServiceImpl(PaperRepository paperRepository, AuthorRepository authorRepository, InstitutionRepository institutionRepository,
                               KeywordRepository keywordRepository, AuthorPaperRepository authorPaperRepository,InstitutionPaperRepository institutionPaperRepository,
                               KeywordPaperRepository keywordPaperRepository,KeywordActivityRepository keywordActivityRepository,
                               AuthorActivityRepository authorActivityRepository,
                               PaperActivityRepository paperActivityRepository,
                               InstitutionActivityRepository institutionActivityRepository) {
        this.paperRepository = paperRepository;
        this.authorRepository = authorRepository;
        this.institutionRepository = institutionRepository;
        this.keywordRepository = keywordRepository;
        this.authorPaperRepository = authorPaperRepository;
        this.institutionPaperRepository = institutionPaperRepository;
        this.keywordPaperRepository = keywordPaperRepository;
        this.keywordActivityRepository = keywordActivityRepository;
        this.authorActivityRepository = authorActivityRepository;
        this.paperActivityRepository = paperActivityRepository;
        this.institutionActivityRepository = institutionActivityRepository;
    }

    @Override
    @Async
    public void updateActivity() {
        List<Paper> papers = paperRepository.findAll();
        for(Paper paper:papers){
            updatePaperActivity(paper);
        }
        List<Author> authors = authorRepository.findAll();
        for(Author author:authors){
            updateAuthorActivity(author);
        }
        List<Institution> institutions = institutionRepository.findAll();
        for(Institution institution:institutions){
            updateInstitutionActivity(institution);
        }
        List<Keyword> keywords = keywordRepository.findAll();
        for(Keyword keyword:keywords){
            updateKeywordActivity(keyword);
        }
    }

    @Override
    public void updatePaperActivity(Paper paper) {
        long totalNum = paperRepository.count();
        double referenceWeight = 1+(double)paperRepository.countByReferenceCountLessThan(paper.getReferenceCount())/totalNum;
        double publicationYearWeight = 1+(double)paperRepository.countByPublicationYearLessThan(paper.getPublicationYear())/totalNum;
        double activity = 10000 * referenceWeight*publicationYearWeight;
        PaperActivity paperActivity = new PaperActivity(paper.getDOI(),paper.getDocumentTitle(),activity,paper.getReferenceCount());
        paperActivityRepository.save(paperActivity);
    }

    @Override
    public void updateAuthorActivity(Author author) {
        int recentYear = TimeUtils.getRecentYear();
        List<Author__Paper> author__papers = authorPaperRepository.findByAuthorAuthorIdOrderByReferenceDesc(author.getAuthorId());
        double activity = 0;
        int citation = 0;
        int publication = author__papers.size();
        int firstAuthor = 0;
        int hindex = countAuthorHindex(author__papers);
        int gindex = countAuthorGindex(author__papers);
        double hgindex = countHGindex(hindex,gindex);
        for(Author__Paper author__paper:author__papers){
            int publicationYear = author__paper.getPaper().getPublicationYear();
            activity += getAuthorWeight(author__paper.getAuthorNumber(),author__paper.getOrderNumber())*Math.pow(0.75,recentYear-publicationYear);
            citation +=author__paper.getReference();
            if (author__paper.getOrderNumber()==1){
                firstAuthor++;
            }
        }
        AuthorActivity authorActivity = new AuthorActivity(author.getAuthorId(),author.getName(),activity,citation,publication,firstAuthor,hindex,gindex,hgindex);
        authorActivityRepository.save(authorActivity);
    }

    @Override
    public void updateInstitutionActivity(Institution institution) {
        List<Institution__Paper> institution__papers = institutionPaperRepository.findByInstitution(institution);
        double activity = countInstitutionActivity(institution.getInstitutionId());
        int citation = 0;
        int publication = institution__papers.size();
        int firstAuthor = 0;
        int hindex = countInstitutionHindex(institution__papers);
        int gindex = countInstitutionGindex(institution__papers);
        double hgindex = countHGindex(hindex,gindex);
        for(Institution__Paper institution__paper:institution__papers){
            citation +=institution__paper.getReference();
            if (institution__paper.getOrderNumber()==1){
                firstAuthor++;
            }
        }
        InstitutionActivity institutionActivity = new InstitutionActivity(institution.getInstitutionId(),institution.getName(),
                activity,citation,publication,firstAuthor,hindex,gindex,hgindex);
        institutionActivityRepository.save(institutionActivity);
    }

    @Override
    public void updateKeywordActivity(Keyword keyword) {
        int recentYear = TimeUtils.getRecentYear();
        List<Keyword__Paper> keyword__papers = keywordPaperRepository.findByKeyword(keyword);
        double activity = 0;
        int citation = 0;
        int publication = keyword__papers.size();
        for(Keyword__Paper keyword__paper:keyword__papers){
            Paper paper = keyword__paper.getPaper();
            activity +=Math.pow(0.75,recentYear-paper.getPublicationYear());
            citation +=keyword__paper.getReference();
        }
        KeywordActivity keywordActivity = new KeywordActivity(keyword.getKeywordId(),keyword.getWord(),activity,citation,publication);
        keywordActivityRepository.save(keywordActivity);
    }

    @Override
    public LinkedList<PaperTrend> getPaperTrend(String sort) {
        List<PaperActivity> paperActivities = new ArrayList<>();
        if(sort.equals("citation")){
            paperActivities = paperActivityRepository.findTop100ByOrderByCitationDesc();
        }
        LinkedList<PaperTrend> paperTrends = new LinkedList<>();
        for(PaperActivity paperActivity:paperActivities){
            paperTrends.add(new PaperTrend(paperActivity.getDocumentTitle(),paperActivity.getDOI(),paperActivity.getCitation()));
        }
        return paperTrends;
    }

    @Override
    public LinkedList<AuthorTrend> getAuthorTrend(String sort) {
        List<AuthorActivity> authorActivities = new ArrayList<>();
        switch (sort) {
            case "activity":
                authorActivities = authorActivityRepository.findTop100ByOrderByActivityDesc();
                break;
            case "citation":
                authorActivities = authorActivityRepository.findTop100ByOrderByCitationDesc();
                break;
            case "publication":
                authorActivities = authorActivityRepository.findTop100ByOrderByPublicationDesc();
                break;
            case "firstAuthor":
                authorActivities = authorActivityRepository.findTop100ByOrderByFirstAuthorDesc();
                break;
            case "hindex":
                authorActivities = authorActivityRepository.findTop100ByOrderByHindexDesc();
                break;
            case "gindex":
                authorActivities = authorActivityRepository.findTop100ByOrderByGindexDesc();
                break;
            case "hgindex":
                authorActivities = authorActivityRepository.findTop100ByOrderByHgindexDesc();
                break;
        }

        LinkedList<AuthorTrend> authorTrends = new LinkedList<>();
        for(AuthorActivity authorActivity:authorActivities){
            authorTrends.add(new AuthorTrend(authorActivity.getName(),authorActivity.getAuthorId()+"",authorActivity.getActivity(),
                    authorActivity.getCitation(),authorActivity.getPublication(),authorActivity.getFirstAuthor(),
                    authorActivity.getHindex(),authorActivity.getGindex(),authorActivity.getHgindex()));
        }
        return authorTrends;
    }

    @Override
    public LinkedList<InstitutionTrend> getInstitutionTrend(String sort) {
        List<InstitutionActivity> institutionActivities = new ArrayList<>();
        switch (sort) {
            case "activity":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByActivityDesc();
                break;
            case "citation":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByCitationDesc();
                break;
            case "publication":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByPublicationDesc();
                break;
            case "firstAuthor":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByFirstAuthorDesc();
                break;
            case "hindex":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByHindexDesc();
                break;
            case "gindex":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByGindexDesc();
                break;
            case "hgindex":
                institutionActivities = institutionActivityRepository.findTop100ByOrderByHgindexDesc();
                break;
        }
        LinkedList<InstitutionTrend> institutionTrends = new LinkedList<>();
        for(InstitutionActivity institutionActivity:institutionActivities){
            institutionTrends.add(new InstitutionTrend(institutionActivity.getName(),institutionActivity.getInstitutionId()+"",
                    institutionActivity.getActivity(),institutionActivity.getCitation(),institutionActivity.getPublication(),
                    institutionActivity.getFirstAuthor(),institutionActivity.getHindex(),institutionActivity.getGindex(),institutionActivity.getHgindex()));
        }
        return institutionTrends;
    }

    @Override
    public LinkedList<KeywordTrend> getKeywordTrend(String sort) {
        List<KeywordActivity> keywordActivities = new ArrayList<>();
        switch (sort) {
            case "activity":
                keywordActivities = keywordActivityRepository.findTop100ByOrderByActivityDesc();
                break;
            case "citation":
                keywordActivities = keywordActivityRepository.findTop100ByOrderByCitationDesc();
                break;
            case "publication":
                keywordActivities = keywordActivityRepository.findTop100ByOrderByPublicationDesc();
                break;
        }
        LinkedList<KeywordTrend> keywordTrends = new LinkedList<>();
        for(KeywordActivity keywordActivity:keywordActivities){
           keywordTrends.add(new KeywordTrend(keywordActivity.getWord(),keywordActivity.getKeywordId()+"",keywordActivity.getActivity(),keywordActivity.getCitation(),keywordActivity.getPublication()));
        }
        return keywordTrends;
    }

    @Override
    public AuthorActivity getAuthorActivity(Long authorId){
        return authorActivityRepository.findAuthorActivityByAuthorId(authorId);
    }

    @Override
    public KeywordActivity getKeywordActivity(Long keywordId) {
        return keywordActivityRepository.findKeywordActivityByKeywordId(keywordId);
    }

    @Override
    public InstitutionActivity getInstitutionActivity(Long institutionId) {
       return institutionActivityRepository.findInstitutionActivityByInstitutionId(institutionId);
    }

    @Override
    public double getAuthorSegmentation(double percentage) {
        int index = (int) Math.ceil(authorActivityRepository.count()*percentage)-1;
        if (index<0){
            index = 0;
        }
        List<AuthorActivity> authorActivities = authorActivityRepository.findByOrderByActivityDesc();
        AuthorActivity authorActivity = authorActivities.get(index);
        return authorActivity!=null?authorActivity.getActivity():0;
    }

    @Override
    public double getInstitutionSegmentation(double percentage) {
        int index = (int) Math.ceil(institutionActivityRepository.count()*percentage)-1;
        if (index<0){
            index = 0;
        }
        List<InstitutionActivity> institutionActivities = institutionActivityRepository.findByOrderByActivityDesc();
        InstitutionActivity institutionActivity = institutionActivities.get(index);
        return institutionActivity!=null?institutionActivity.getActivity():0;
    }

    @Override
    public double getKeywordSegmentation(double percentage) {
        int index = (int) Math.ceil(keywordActivityRepository.count()*percentage)-1;
        if (index<0){
            index = 0;
        }
        List<KeywordActivity> keywordActivities = keywordActivityRepository.findByOrderByActivityDesc();
        KeywordActivity keywordActivity = keywordActivities.get(index);
        return keywordActivity!=null?keywordActivity.getActivity():0;
    }

    private int countAuthorHindex(List<Author__Paper> author__papers){
        int hindex = 0;
        for(Author__Paper author__paper:author__papers){
            if (author__paper.getReference()>hindex){
                hindex++;
            }else {
                break;
            }
        }
        return hindex;
    }

    private int countAuthorGindex(List<Author__Paper> author__papers){
        int gindex = 0;
        int citation = 0;
        for(Author__Paper author__paper:author__papers){
            citation += author__paper.getReference();
            if (citation>Math.pow(gindex,2)){
                gindex++;
            }else {
                break;
            }
        }
        return gindex;
    }

    private int countInstitutionHindex(List<Institution__Paper> institution__papers){
        int hindex = 0;
        for(Institution__Paper institution__paper:institution__papers){
            if (institution__paper.getReference()>hindex){
                hindex++;
            }else {
                break;
            }
        }
        return hindex;
    }

    private int countInstitutionGindex(List<Institution__Paper> institution__papers){
        int gindex = 0;
        int citation = 0;
        for(Institution__Paper institution__paper:institution__papers){
            citation += institution__paper.getReference();
            if (citation>Math.pow(gindex,2)){
                gindex++;
            }else {
                break;
            }
        }
        return gindex;
    }

    private double getAuthorWeight(int authorNum,int order){
        if (order==1){
            double result = 1;
            for(int i = 1;i<authorNum;i++){
                System.out.println(result);
                result =result -  1.0/(i+authorNum);
            }
            return result;
        }else {
            return 1.0/(order+authorNum-1);
        }
    }

    private double countHGindex(int hindex, int gindex){
        return Math.sqrt(hindex*gindex);
    }

    private double countInstitutionActivity(Long institutionId){
        try{
            return Double.parseDouble(String.valueOf(institutionActivityRepository.countInstitutionActivity(institutionId).get(0)[0]));
        }catch(Exception ex){
            return 0;
        }
    }
}
