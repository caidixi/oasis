package cmcm.oasis.repository;

import cmcm.oasis.module.Author;
import cmcm.oasis.module.Institution;
import cmcm.oasis.module.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Author findByName(String name);

    Author findAuthorByAuthorId(Long id);

    Author findByNameAndInstitution(String name,Institution institution);

    List<Author> findByInstitution(Institution institution);
}
