package cmcm.oasis.repository;

import cmcm.oasis.module.PaperActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaperActivityRepository extends JpaRepository<PaperActivity,Long> {
    List<PaperActivity> findTop100ByOrderByCitationDesc();
}
