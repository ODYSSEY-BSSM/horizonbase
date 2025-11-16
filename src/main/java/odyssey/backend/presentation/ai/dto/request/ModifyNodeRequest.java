package odyssey.backend.presentation.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.presentation.ai.dto.request.vo.ModifyNodeVO;
import odyssey.backend.presentation.ai.dto.request.vo.NodeCourseRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ModifyNodeRequest {

    @NotBlank(message = "필수값입니다.")
    private String description;

    @NotBlank(message = "필수값입니다.")
    private List<ModifyNodeVO> nodes;

    @NotBlank(message = "필수값입니다.")
    private List<NodeCourseRequest> courses;

}
