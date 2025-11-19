package odyssey.backend.presentation.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenerateQuizRequest {

    @NotBlank(message = "필수값입니다.")
    private String title;

    @NotBlank(message = "필수값입니다.")
    private String description;

}
