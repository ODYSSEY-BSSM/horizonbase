package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RoadmapResponseVO(
        Long id,
        String title,
        String description,
        LocalDate lastModifiedAt,
        LocalDateTime lastAccessedAt,
        boolean isFavorite
) {
    public static RoadmapResponseVO from(Roadmap roadmap) {
        return new RoadmapResponseVO(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getLastModifiedAt(),
                roadmap.getLastAccessedAt(),
                roadmap.getIsFavorite()
        );
    }
}
