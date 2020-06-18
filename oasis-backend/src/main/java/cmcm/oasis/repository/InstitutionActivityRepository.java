package cmcm.oasis.repository;

import cmcm.oasis.module.InstitutionActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InstitutionActivityRepository extends JpaRepository<InstitutionActivity,Long> {
    InstitutionActivity findInstitutionActivityByInstitutionId(Long id);

    List<InstitutionActivity> findTop100ByOrderByActivityDesc();

    List<InstitutionActivity> findTop100ByOrderByCitationDesc();

    List<InstitutionActivity> findTop100ByOrderByPublicationDesc();

    List<InstitutionActivity> findTop100ByOrderByFirstAuthorDesc();

    List<InstitutionActivity> findTop100ByOrderByHindexDesc();

    List<InstitutionActivity> findTop100ByOrderByGindexDesc();

    List<InstitutionActivity> findTop100ByOrderByHgindexDesc();

    List<InstitutionActivity> findByOrderByActivityDesc();

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(nativeQuery = true,value = "select sum(activity) from author_activity where author_id in (select author.author_id from author where author.institution_id = ?1)")
    List<Object[]> countInstitutionActivity(Long institutionId);

}
