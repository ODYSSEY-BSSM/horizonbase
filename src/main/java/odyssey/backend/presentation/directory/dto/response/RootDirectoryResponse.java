package odyssey.backend.presentation.directory.dto.response;

import odyssey.backend.domain.directory.Directory;
import odyssey.backend.presentation.roadmap.dto.response.SecondSimpleRoadmapResponse;

import java.util.List;

public record RootDirectoryResponse(
        Long id,
        String name,
        String description,
        Integer roadmapCount,
        List<SecondSimpleRoadmapResponse> roadmaps
) {
    public static RootDirectoryResponse from(Directory directory) {
        return new RootDirectoryResponse(
                directory.getId(),
                directory.getName(),
                directory.getDescription(),
                directory.roadmapCount(),
                directory.getRoadmaps()
                        .stream()
                        .map(SecondSimpleRoadmapResponse::from)
                        .toList()
        );
    }
}
