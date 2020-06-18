package cmcm.oasis.repository;

import cmcm.oasis.module.Keyword;
import cmcm.oasis.module.Keyword__Paper;
import cmcm.oasis.module.Paper;
import org.hibernate.mapping.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface KeywordPaperRepository extends JpaRepository<Keyword__Paper,Long> {

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select keyword_keyword_id,count(paper_doi) from keyword__paper where paper_doi in" +
            "                                                                     (select paper_doi from author__paper where author__paper.author_author_id = ?1)" +
            "group by keyword_keyword_id order by count(paper_doi) desc",nativeQuery = true)
    List<Object[]> findFieldOfResearchByAuthorId(Long authorId);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select sum(author__paper.reference) from author__paper where paper_doi in (select paper_doi from keyword__paper where keyword__paper.keyword_keyword_id = ?1) and author_author_id = ?2",nativeQuery = true)
    List<Object[]> countReferenceByKeywordAndAuthor(Long keywordId,Long authorId);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select sum(reference) from institution__paper where paper_doi in (select paper_doi from keyword__paper where keyword__paper.keyword_keyword_id = ?1) and institution_institution_id = ?2",nativeQuery = true)
    List<Object[]> countReferenceByKeywordAndInstitution(Long keywordId,Long institutionId);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select keyword_keyword_id,count(paper_doi) from keyword__paper where paper_doi in" +
            "                                                                     (select paper_doi from institution__paper where institution__paper.institution_institution_id = ?1)" +
            "group by keyword_keyword_id order by count(paper_doi) desc",nativeQuery = true)
    List<Object[]> findFieldOfResearchByInstitutionId(Long institutionId);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select keyword_keyword_id,count(paper_doi) from keyword__paper where paper_doi in" +
            "                                                                     (select DOI from paper where paper.conference_id = ?1)" +
            "group by keyword_keyword_id order by count(paper_doi) desc",nativeQuery = true)
    List<Object[]> findFieldOfResearchByConferenceId(Long conferenceId);

    List<Keyword__Paper> findByKeyword(Keyword keyword);

    int countByKeywordAndPaperIn(Keyword keyword, Collection<Paper> papers);
}
