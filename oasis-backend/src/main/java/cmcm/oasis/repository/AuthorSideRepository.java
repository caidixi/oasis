package cmcm.oasis.repository;

import cmcm.oasis.module.Author;
import cmcm.oasis.module.AuthorSide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorSideRepository extends JpaRepository<AuthorSide,Long> {
    AuthorSide findBySourceAndTarget(Author source,Author target);

    List<AuthorSide> findTop1000ByOrderByWeightDesc();
}
