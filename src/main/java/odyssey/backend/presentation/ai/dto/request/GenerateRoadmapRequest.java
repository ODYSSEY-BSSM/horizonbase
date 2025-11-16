package odyssey.backend.presentation.ai.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.presentation.ai.dto.request.vo.CourseRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenerateRoadmapRequest {

    @NotBlank(message = "필수값입니다.")
    private String description;

    @NotBlank(message = "필수값입니다.")
    private String language;

    @Valid
    @NotEmpty(message = "필수값입니다.")
    private List<CourseRequest> courses;

}
