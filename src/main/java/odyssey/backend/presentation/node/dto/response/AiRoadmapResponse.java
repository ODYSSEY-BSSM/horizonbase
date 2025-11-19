package odyssey.backend.presentation.node.dto.response;

import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.presentation.roadmap.dto.response.SimpleRoadmapResponse;

import java.util.List;

public record AiRoadmapResponse(
        SimpleRoadmapResponse roadmap,
        List<NodeResponse> nodes
) {
    public static AiRoadmapResponse from(
            Roadmap roadmap,
            List<Node> nodes
    ){
        return new AiRoadmapResponse(
                SimpleRoadmapResponse.from(roadmap),
                nodes
                        .stream()
                        .map(NodeResponse::from)
                        .toList()
        );
    }
}
