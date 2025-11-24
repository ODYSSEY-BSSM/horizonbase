package odyssey.backend.infrastructure.persistence.section;

import odyssey.backend.domain.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
