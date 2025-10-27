package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.UpdatePasswordVerification;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.InvalidRequestEmailException;
import odyssey.backend.infrastructure.persistence.auth.UpdatePasswordVerificationRepository;
import odyssey.backend.infrastructure.persistence.auth.UserRepository;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase {

    private final UpdatePasswordVerificationRepository updatePasswordVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void updatePassword(User user, UpdatePasswordRequest request) {
        validate(user, request);
        user.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    private void validate(User user, UpdatePasswordRequest request) {
        UpdatePasswordVerification updatePasswordVerification = updatePasswordVerificationRepository.findById(user.getEmail())
                .orElseThrow(InvalidRequestEmailException::new);

        updatePasswordVerification.validateVerified();

        updatePasswordVerificationRepository.delete(updatePasswordVerification);
    }
}
