package cmcm.oasis.repository;

import cmcm.oasis.module.Author;
import cmcm.oasis.module.Author__Paper;
import cmcm.oasis.module.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AuthorPaperRepository extends JpaRepository<Author__Paper,Long> {

    int countByAuthor(Author author);

    List<Author__Paper> findByPaper(Paper paper);

    List<Author__Paper> findByPaperAndAuthorNotIn(Paper paper,Collection<Author> authors);

    List<Author__Paper> findByAuthorAuthorIdOrderByReferenceDesc(long AuthorId);

    /** @noinspection SpringDataRepositoryMethodReturnTypeInspection*/
    @Query(value = "select author_author_id,count(paper_doi) from author__paper where paper_doi in" +
            "                                                                     (select keyword__paper.paper_doi from keyword__paper where keyword_keyword_id = ?1)" +
            "group by author_author_id order by count(paper_doi) desc",nativeQuery = true)
    List<Object[]> findRelatedAuthorsByKeywordId(Long keywordId);
}
