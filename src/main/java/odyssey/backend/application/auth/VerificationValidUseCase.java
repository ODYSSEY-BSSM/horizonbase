package odyssey.backend.application.auth;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.SignUpVerification;
import odyssey.backend.domain.auth.exception.InvalidRequestEmailException;
import odyssey.backend.infrastructure.persistence.auth.SignUpVerificationRepository;
import odyssey.backend.presentation.auth.dto.request.VerifyRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationValidUseCase {

    private final SignUpVerificationRepository signUpVerificationRepository;

    public void verify(VerifyRequest request){
        SignUpVerification signUpVerification = signUpVerificationRepository.findById(request.getEmail())
                .orElseThrow(InvalidRequestEmailException::new);

        signUpVerification.validateCode(request.getCode());

        signUpVerification.verify();

        signUpVerificationRepository.save(signUpVerification);
    }

}
