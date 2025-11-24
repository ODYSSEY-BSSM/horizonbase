package odyssey.backend.presentation.text.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.text.Type;


@Getter
@NoArgsConstructor
public class UpdateTextRequest {

    @NotBlank(message = "필수값입니다.")
    private String text;

    @NotNull(message = "필수값이빈다.")
    private Type type;

    @NotNull(message = "필수값입니다.")
    private Long sectionId;

}
