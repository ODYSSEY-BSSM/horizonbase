package odyssey.backend.presentation.problem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProblemRequest {

    @NotBlank(message = "필수값입니다.")
    private String title;

    @NotEmpty(message = "필수값입니다.")
    private List<String> choices;

    @NotNull(message = "필수값입니다.")
    private Integer correct;

}
