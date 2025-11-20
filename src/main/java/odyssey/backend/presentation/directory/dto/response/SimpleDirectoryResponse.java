package odyssey.backend.presentation.directory.dto.response;

import odyssey.backend.domain.directory.Directory;

public record SimpleDirectoryResponse(
        Long id,
        String name,
        String description
) {
    static public SimpleDirectoryResponse from(Directory directory) {
        return new SimpleDirectoryResponse(
                directory.getId(),
                directory.getName(),
                directory.getDescription()
        );
    }
}
