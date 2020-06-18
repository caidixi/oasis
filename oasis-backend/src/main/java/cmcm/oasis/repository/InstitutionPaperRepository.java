package cmcm.oasis.repository;

import cmcm.oasis.module.Institution;
import cmcm.oasis.module.Institution__Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstitutionPaperRepository extends JpaRepository<Institution__Paper,Long> {
    int countByInstitution(Institution institution);

    Institution__Paper findByInstitutionAndPaperDOI(Institution institution, String doi);

    List<Institution__Paper> findByInstitution(Institution institution);

    List<Institution__Paper> findByInstitutionInstitutionId(Long id);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select institution_institution_id,count(paper_doi) from institution__paper where paper_doi in" +
            "                                                                     (select keyword__paper.paper_doi from keyword__paper where keyword_keyword_id = ?1)" +
            "group by institution_institution_id order by count(paper_doi) desc",nativeQuery = true)
    List<Object[]> findRelatedInstitutionsByKeywordId(Long keywordId);
}
