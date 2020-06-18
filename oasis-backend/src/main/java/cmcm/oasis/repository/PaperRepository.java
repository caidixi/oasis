package cmcm.oasis.repository;

import cmcm.oasis.module.Conference;
import cmcm.oasis.module.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PaperRepository extends JpaRepository<Paper,String> {
    Paper findByDOI(String doi);

    List<Paper> findByConferenceConferenceId(Long id);

    List<Paper> findByAuthorsContainsAndAuthorAffiliationsContainsAndConference_NameContainsAndAuthorKeywordsContainsAndPublicationYearGreaterThanEqualAndPublicationYearLessThanEqual(String authors, String institute, String conference, String keywords,int startYear,int endYear);

    List<Paper> findTop3ByOrderByReferenceCountDesc();

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(nativeQuery = true,value = "select sum(reference_count) from paper where doi in (select paper_doi from author__paper where author__paper.author_author_id = ?1)")
    List<Object[]> findReferenceCountByAuthorId(long authorId);

    List<Paper> findByDOIInOrderByReferenceCountDesc(Collection<String> dois);

    List<Paper> findByConferenceOrderByReferenceCountDesc(Conference conference);

    int countByPublicationYearAndDOIIn(int year,Collection<String> dois);

    int countByReferenceCountLessThan(int referenceCount);

    int countByPublicationYearLessThan(int publicationYear);

    Paper findTop1ByOrderByPublicationYearDesc();

    Paper findTop1ByOrderByPublicationYearAsc();
}
