package odyssey.backend.presentation.node.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import odyssey.backend.domain.node.Subject;

@Getter
@AllArgsConstructor
public class SubjectRequest {

    @NotBlank(message = "필수값입니다.")
    private Subject subject;

}
