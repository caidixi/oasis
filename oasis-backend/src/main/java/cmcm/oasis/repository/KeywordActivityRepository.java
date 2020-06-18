package cmcm.oasis.repository;

import cmcm.oasis.module.KeywordActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface KeywordActivityRepository extends JpaRepository<KeywordActivity,Long> {
    KeywordActivity findKeywordActivityByKeywordId(Long id);

    List<KeywordActivity> findTop100ByOrderByActivityDesc();

    List<KeywordActivity> findTop100ByOrderByCitationDesc();

    List<KeywordActivity> findTop100ByOrderByPublicationDesc();

    List<KeywordActivity> findByOrderByActivityDesc();
}
