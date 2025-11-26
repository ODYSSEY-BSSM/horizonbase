package odyssey.backend.presentation.education.dto.response;

import odyssey.backend.domain.node.Subject;
import odyssey.backend.presentation.education.dto.response.vo.SubjectVo;

import java.util.List;

public record EducationResponse(
        List<SubjectVo> subjects
) {
    public static EducationResponse from(List<Subject> subjects) {
        return new EducationResponse(
                subjects.stream()
                        .map(SubjectVo::from)
                        .toList()
        );
    }
}
