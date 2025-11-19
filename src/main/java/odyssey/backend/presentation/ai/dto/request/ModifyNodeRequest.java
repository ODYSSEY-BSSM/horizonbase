package odyssey.backend.presentation.ai.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.presentation.ai.dto.request.vo.ModifyNodeVO;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ModifyNodeRequest {

    @NotBlank(message = "필수값입니다.")
    private String description;

    @Valid
    @NotEmpty(message = "필수값입니다.")
    private List<ModifyNodeVO> nodes;

}
