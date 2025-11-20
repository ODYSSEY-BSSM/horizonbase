package odyssey.backend.presentation.directory.dto.response;

import odyssey.backend.domain.directory.Directory;

public record DirectoryInfoResponse(
        Long id,
        String description,
        String name,
        Integer roadmapCount,
        Long completedRoadmapCount,
        String lastAcessedRoadmapName
) {
    public static DirectoryInfoResponse from(Directory directory) {
        return new DirectoryInfoResponse(
                directory.getId(),
                directory.getName(),
                directory.getDescription(),
                directory.roadmapCount(),
                directory.completedRoadmapCount(),
                directory.getLastAcessedRoadmapName()
        );
    }
}
