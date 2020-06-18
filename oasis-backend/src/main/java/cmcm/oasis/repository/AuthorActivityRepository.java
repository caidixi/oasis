package cmcm.oasis.repository;

import cmcm.oasis.module.AuthorActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AuthorActivityRepository extends JpaRepository<AuthorActivity,Long> {
    AuthorActivity findAuthorActivityByAuthorId(Long id);

    List<AuthorActivity> findTop100ByOrderByActivityDesc();

    List<AuthorActivity> findTop100ByOrderByCitationDesc();

    List<AuthorActivity> findTop100ByOrderByPublicationDesc();

    List<AuthorActivity> findTop100ByOrderByFirstAuthorDesc();

    List<AuthorActivity> findTop100ByOrderByHindexDesc();

    List<AuthorActivity> findTop100ByOrderByGindexDesc();

    List<AuthorActivity> findTop100ByOrderByHgindexDesc();

    List<AuthorActivity> findByOrderByActivityDesc();
}
