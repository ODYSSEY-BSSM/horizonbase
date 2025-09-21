package odyssey.backend.domain.problem.exception;

import odyssey.backend.domain.problem.exception.error.ProblemExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class ProblemNotFoundException extends GlobalException {
    public ProblemNotFoundException() {
        super(ProblemExceptionProperty.PROBLEM_NOT_FOUND);
    }
}
