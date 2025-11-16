package odyssey.backend.presentation.ai.dto.request.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.node.NodeType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ModifyNodeVO {

    @NotBlank(message = "필수값입니다.")
    private Long id;

    @NotBlank(message = "필수값입니다.")
    private String title;

    @NotBlank(message = "필수값입니다.")
    private String description;

    @NotBlank(message = "필수값입니다.")
    private NodeType type;

    @NotNull(message = "필수값입니다.")
    private Integer x;

    @NotNull(message = "필수값입니다.")
    private Integer y;

    @NotBlank(message = "필수값입니다.")
    private String category;

    @NotBlank(message = "필수값입니다.")
    private Long parentNodeId;

}
