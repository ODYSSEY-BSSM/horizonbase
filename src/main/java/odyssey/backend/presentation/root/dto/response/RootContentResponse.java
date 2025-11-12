package odyssey.backend.presentation.root.dto.response;

import odyssey.backend.presentation.directory.dto.response.SimpleDirectoryResponse;

import java.util.List;

public record RootContentResponse(
        List<SimpleDirectoryResponse> directories
) {
    public static RootContentResponse from(List<SimpleDirectoryResponse> directories) {
        return new RootContentResponse(directories);
    }
}
