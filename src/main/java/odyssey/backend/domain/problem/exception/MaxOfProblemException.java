package odyssey.backend.domain.problem.exception;

import odyssey.backend.domain.problem.exception.error.ProblemExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class MaxOfProblemException extends GlobalException {
    public MaxOfProblemException() {
        super(ProblemExceptionProperty.MAX_OF_PROBLEM_IN_NODE);
    }
}
