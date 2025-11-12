package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.auth.UserFacade;
import odyssey.backend.domain.auth.UpdatePasswordVerification;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.InvalidRequestEmailException;
import odyssey.backend.infrastructure.persistence.auth.UpdatePasswordVerificationRepository;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase {

    private final UserFacade userFacade;
    private final UpdatePasswordVerificationRepository updatePasswordVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword(UpdatePasswordRequest request) {
        User currentUser = userFacade.getUserByEmail(request.getEmail());
        validate(currentUser, request);
        currentUser.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    private void validate(User user, UpdatePasswordRequest request) {
        UpdatePasswordVerification updatePasswordVerification = updatePasswordVerificationRepository.findById(user.getEmail())
                .orElseThrow(InvalidRequestEmailException::new);

        updatePasswordVerification.validateVerified();

        updatePasswordVerificationRepository.delete(updatePasswordVerification);
    }
}
