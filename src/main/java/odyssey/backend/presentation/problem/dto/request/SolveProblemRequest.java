package odyssey.backend.presentation.problem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolveProblemRequest {

    @NotNull(message = "필수값입니다.")
    private Integer answer;

}
