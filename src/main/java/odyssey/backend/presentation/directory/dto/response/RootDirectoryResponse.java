package odyssey.backend.presentation.directory.dto.response;

import odyssey.backend.domain.directory.Directory;
import odyssey.backend.presentation.roadmap.dto.response.SimpleRoadmapResponse;

import java.util.List;

public record RootDirectoryResponse(
        Long id,
        String name,
        String description,
        List<SimpleRoadmapResponse> roadmaps
) {
    public static RootDirectoryResponse from(Directory directory) {
        return new RootDirectoryResponse(
                directory.getId(),
                directory.getName(),
                directory.getDescription(),
                directory.getRoadmaps()
                        .stream()
                        .map(SimpleRoadmapResponse::from)
                        .toList()
        );
    }
}
