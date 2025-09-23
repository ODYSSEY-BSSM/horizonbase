package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.SignUpVerification;
import odyssey.backend.domain.auth.exception.InvalidRequestEmailException;
import odyssey.backend.infrastructure.persistence.auth.SignUpVerificationRepository;
import odyssey.backend.presentation.user.dto.request.VerifyRequest;
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
