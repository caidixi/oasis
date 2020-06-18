package cmcm.oasis.repository;

import cmcm.oasis.module.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference,Long> {
    Conference findByName(String name);

    Conference findConferenceByConferenceId(Long id);
}
