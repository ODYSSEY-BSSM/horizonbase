package odyssey.backend.infrastructure.persistence.text;

import odyssey.backend.domain.text.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Long> {
}
