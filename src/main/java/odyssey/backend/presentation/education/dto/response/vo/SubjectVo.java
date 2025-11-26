package odyssey.backend.presentation.education.dto.response.vo;

import odyssey.backend.domain.node.Subject;

public record SubjectVo(
        String subjectName,
        String description
) {
    public static SubjectVo from(Subject subject){
        return new SubjectVo(
                subject.getTitle(),
                subject.getDescription()
        );
    }
}
