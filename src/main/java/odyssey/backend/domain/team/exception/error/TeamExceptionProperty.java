package odyssey.backend.domain.team.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TeamExceptionProperty implements ErrorProperty {
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀이 존재하지 않습니다"),
    IS_NOT_LEADER(HttpStatus.FORBIDDEN, "팀장이 아닙니다."),
    FAILED_CREATE_INVITE_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "초대코드 생성에 실패하였습니다.");

    private final HttpStatus status;
    private final String message;
}
