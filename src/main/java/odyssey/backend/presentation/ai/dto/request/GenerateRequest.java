package odyssey.backend.presentation.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenerateRequest {

    @NotBlank(message = "필수값입니다.")
    private String prompt;

}
