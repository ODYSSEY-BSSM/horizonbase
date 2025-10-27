package odyssey.backend.infrastructure.persistence.auth;

import odyssey.backend.domain.auth.UpdatePasswordVerification;
import org.springframework.data.repository.CrudRepository;

public interface UpdatePasswordVerificationRepository extends CrudRepository<UpdatePasswordVerification, String> {
}
