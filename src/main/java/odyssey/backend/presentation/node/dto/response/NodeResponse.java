package odyssey.backend.presentation.node.dto.response;

import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.node.NodeType;
import odyssey.backend.shared.color.Color;

import java.util.List;

public record NodeResponse(
        Long id,
        String title,
        String description,
        Integer height,
        Integer width,
        NodeType type,
        Integer x,
        Integer y,
        Color color,
        Long roadmapId,
        Long parentNodeId,
        List<SimpleNodeResponse> childNode,
        Integer progress,
        boolean isEducation,
        String subject
) {
    public static NodeResponse from(Node node) {
        List<SimpleNodeResponse> children = node.getChildren() != null
                ? node.getChildren().stream()
                .map(SimpleNodeResponse::from)
                .toList()
                : List.of();

        Integer progress = node.getType() == NodeType.BOTTOM ? node.getProgress() : null;

        String subject = node.getSubject() == null ? null : node.getSubject().getTitle();

        return new NodeResponse(
                node.getId(),
                node.getTitle(),
                node.getDescription(),
                node.getHeight(),
                node.getWidth(),
                node.getType(),
                node.getX(),
                node.getY(),
                node.getColor(),
                node.getRoadmap().getId(),
                node.getParent() != null ? node.getParent().getId() : null,
                children,
                progress,
                node.isEducation(),
                subject
        );
    }
}
