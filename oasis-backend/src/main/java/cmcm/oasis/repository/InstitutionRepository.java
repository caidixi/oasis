package cmcm.oasis.repository;

import cmcm.oasis.module.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution,Long> {
    Institution findByName(String name);

    Institution findInstitutionByInstitutionId(Long institutionId);
}
