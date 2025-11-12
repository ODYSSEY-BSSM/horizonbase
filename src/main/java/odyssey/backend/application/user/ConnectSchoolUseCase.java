package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.auth.UserFacade;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.NotSchoolEmailException;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectSchoolUseCase {

    private final UserFacade userFacade;

    @Transactional
    public UserResponse ConnectSchool(User user) {
        User student = userFacade.getUserByEmail(user.getEmail());
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
