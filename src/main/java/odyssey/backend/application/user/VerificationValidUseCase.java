package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.SignUpVerification;
import odyssey.backend.domain.auth.UpdatePasswordVerification;
import odyssey.backend.domain.auth.exception.InvalidRequestEmailException;
import odyssey.backend.infrastructure.persistence.auth.SignUpVerificationRepository;
import odyssey.backend.infrastructure.persistence.auth.UpdatePasswordVerificationRepository;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordVerifyRequest;
import odyssey.backend.presentation.user.dto.request.VerifyRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationValidUseCase {

    private final SignUpVerificationRepository signUpVerificationRepository;
    private final UpdatePasswordVerificationRepository updatePasswordVerificationRepository;

    public void verify(VerifyRequest request){
        SignUpVerification signUpVerification = signUpVerificationRepository.findById(request.getEmail())
                .orElseThrow(InvalidRequestEmailException::new);

        signUpVerification.validateCode(request.getCode());

        signUpVerification.verify();

        signUpVerificationRepository.save(signUpVerification);
    }

    public void verify(UpdatePasswordVerifyRequest request){
        UpdatePasswordVerification updatePasswordVerification = updatePasswordVerificationRepository.findById(request.getEmail())
                .orElseThrow(InvalidRequestEmailException::new);

        updatePasswordVerification.validateCode(request.getCode());

        updatePasswordVerification.verify();

        updatePasswordVerificationRepository.save(updatePasswordVerification);
    }

}
