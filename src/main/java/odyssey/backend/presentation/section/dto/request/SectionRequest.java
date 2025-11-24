package odyssey.backend.presentation.section.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SectionRequest {

    @NotBlank(message = "필수값입니다.")
    private String name;

    @NotNull(message = "필수값입니다.")
    private Integer x;

    @NotNull(message = "필수값입니다.")
    private Integer y;

    @NotNull(message = "필수값입니다.")
    private Integer width;

    @NotNull(message = "필수값입니다.")
    private Integer height;

}
