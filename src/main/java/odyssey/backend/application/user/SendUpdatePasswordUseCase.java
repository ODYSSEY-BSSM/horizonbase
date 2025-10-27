package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.UpdatePasswordVerification;
import odyssey.backend.infrastructure.mail.MailUtil;
import odyssey.backend.infrastructure.persistence.auth.UpdatePasswordVerificationRepository;
import odyssey.backend.presentation.user.dto.request.SendVerificationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendUpdatePasswordUseCase {

    private final MailUtil mailUtil;
    private final UpdatePasswordVerificationRepository updatePasswordVerificationRepository;

    public void sendUpdatePasswordVerificationMail(SendVerificationRequest request){
        UpdatePasswordVerification updatePasswordVerification = new UpdatePasswordVerification(request.getEmail());

        mailUtil.sendMimeMessage(updatePasswordVerification.getEmail(), updatePasswordVerification.getCode());

        updatePasswordVerificationRepository.save(updatePasswordVerification);
    }

}
