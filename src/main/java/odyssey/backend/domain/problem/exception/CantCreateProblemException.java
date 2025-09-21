package odyssey.backend.domain.problem.exception;

import odyssey.backend.domain.problem.exception.error.ProblemExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class CantCreateProblemException extends GlobalException {
    public CantCreateProblemException() {
        super(ProblemExceptionProperty.CANT_CREATE_PROBLEM);
    }
}
