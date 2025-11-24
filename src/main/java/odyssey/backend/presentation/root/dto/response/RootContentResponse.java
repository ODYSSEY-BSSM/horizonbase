package odyssey.backend.presentation.root.dto.response;

import odyssey.backend.presentation.directory.dto.response.RootDirectoryResponse;

import java.util.List;

public record RootContentResponse(
        List<RootDirectoryResponse> directories
) {
    public static RootContentResponse from(List<RootDirectoryResponse> directories) {
        return new RootContentResponse(directories);
    }
}
