package odyssey.backend.presentation.node.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.node.Subject;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {

    @NotNull(message = "필수값입니다.")
    private Subject subject;

}
