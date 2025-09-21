package odyssey.backend.domain.problem.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProblemExceptionProperty implements ErrorProperty {
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 문제입니다."),
    CANT_CREATE_PROBLEM(HttpStatus.BAD_REQUEST, "문제는 최하단 노드에만 만들 수 있습니다."),
    MAX_OF_PROBLEM_IN_NODE(HttpStatus.CONFLICT, "문제는 3개가 최대입니다.");

    private final HttpStatus status;
    private final String message;
}
