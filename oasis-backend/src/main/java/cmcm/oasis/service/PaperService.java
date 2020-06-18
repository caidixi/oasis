package cmcm.oasis.service;

import cmcm.oasis.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface PaperService {

    Response crawlData(String conference,int start,int end);

    Response LoadData(MultipartFile file);

    Response queryInterval();

    Response queryPaper(String authors,String institute,String conference,String keyword,int startYear,int endYear);

    Response queryPaperDetail(String DOI);

    Response queryAuthorDetail(Long authorId);

    Response queryInstituteDetail(Long institutionId);

    Response queryConferenceDetail(Long conferenceId);

    Response queryResearchDirectionDetail(Long researchDirectionId);

    Response getPaperTrend(String sort);

    Response getAuthorTrend(String sort);

    Response getInstituteTrend(String sort);

    Response getResearchHotSpots(String sort);

    Response getInterestingPoints();

    Response getSegmentation(String type,double percentage);

    Response getMainAuthorGraph();
}
