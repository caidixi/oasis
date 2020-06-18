package cmcm.oasis.repository;

import cmcm.oasis.module.Keyword;
import cmcm.oasis.module.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {
    Keyword findKeywordByKeywordId(Long id);

    Keyword findKeywordByWord(String word);
}
