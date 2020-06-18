package cmcm.oasis.service;

import cmcm.oasis.module.*;
import cmcm.oasis.response.body.trend.*;

import java.util.LinkedList;

public interface ActivityService {

    void updateActivity();

    void updatePaperActivity(Paper paper);

    void updateAuthorActivity(Author author);

    void updateInstitutionActivity(Institution institution);

    void updateKeywordActivity(Keyword keyword);

    LinkedList<PaperTrend> getPaperTrend(String sort);

    LinkedList<AuthorTrend> getAuthorTrend(String sort);

    LinkedList<InstitutionTrend> getInstitutionTrend(String sort);

    LinkedList<KeywordTrend> getKeywordTrend(String sort);

    AuthorActivity getAuthorActivity(Long authorId);

    KeywordActivity getKeywordActivity(Long keywordId);

    InstitutionActivity getInstitutionActivity(Long institutionId);

    double getAuthorSegmentation(double percentage);

    double getInstitutionSegmentation(double percentage);

    double getKeywordSegmentation(double percentage);
}
