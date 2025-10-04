package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.NotSchoolEmailException;
import odyssey.backend.domain.auth.service.FindUserService;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectSchoolUseCase {

    private final FindUserService findUserService;

    @Transactional
    public UserResponse ConnectSchool(User user) {
        User student = findUserService.findUserByEmail(user.getEmail());
        validate(student);
        student.connectSchool();
        return UserResponse.from(student);
    }

    public void validate(User user){
        String email = user.getEmail();
        if(!email.endsWith("@bssm.hs.kr")){
            throw new NotSchoolEmailException();
        }
    }

}
