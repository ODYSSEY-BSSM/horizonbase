package odyssey.backend.application.node;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.node.exception.NodeNotFoundException;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.node.dto.request.SubjectRequest;
import odyssey.backend.presentation.node.dto.response.NodeResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final RoadmapRepository roadmapRepository;
    private final NodeRepository nodeRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public NodeResponse createNode(Long roadmapId ,NodeRequest request) {

        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        Node parentNode = null;

        if (request.getParentNodeId() != null) {
            parentNode = nodeRepository.findById(request.getParentNodeId())
                    .orElseThrow(NodeNotFoundException::new);
        }

        Node node = nodeRepository.save(
                Node.from(request, roadmap, parentNode)
        );

        NodeResponse response = NodeResponse.from(node);
        
        if (roadmap.getTeam() != null) {
            messagingTemplate.convertAndSend("/topic/node/roadmap/" + roadmapId + "/created", response);
        }

        return response;
    }

    @Transactional
    public List<NodeResponse> getNodesByRoadmapId(Long roadmapId) {
        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastAccessedAt();

        List<Node> nodes = nodeRepository.findByRoadmapId(roadmapId);

        return nodes.stream()
                .filter(Node::isNotHaveParent)
                .map(NodeResponse::from)
                .toList();
    }

    public NodeResponse getNodeByIdAndRoadmapId(Long nodeId, Long roadmapId) {
        Node node = findByIdAndRoadmapId(nodeId, roadmapId);

        return NodeResponse.from(node);
    }

    @Transactional
    public NodeResponse updateNode(Long nodeId, Long roadmapId, NodeRequest request) {
        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        Node node = findByIdAndRoadmapId(nodeId, roadmapId);

        node.update(
                request.getTitle(),
                request.getDescription(),
                request.getHeight(),
                request.getWidth(),
                request.getType(),
                request.getX(),
                request.getY(),
                request.getCategory());

        return NodeResponse.from(node);
    }

    @Transactional
    public void deleteNodeByIdAndRoadmapId(Long nodeId, Long roadmapId) {
        Node node = findByIdAndRoadmapId(nodeId, roadmapId);

        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        if (roadmap.getTeam() != null) {
            messagingTemplate.convertAndSend("/topic/node/roadmap/" + roadmapId + "/deleted", nodeId);
        }

        nodeRepository.delete(node);
    }

    private Roadmap getRoadmapById(Long roadmapId) {
        return roadmapRepository.findById(roadmapId)
                        .orElseThrow(RoadmapNotFoundException::new);
    }

    private Node findByIdAndRoadmapId(Long nodeId, Long roadmapId) {
        return nodeRepository.findByIdAndRoadmapId(nodeId, roadmapId)
                .orElseThrow(NodeNotFoundException::new);
    }

    @Transactional
    public NodeResponse changeEducation(Long nodeId, Long roadmapId, SubjectRequest request){
        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        Node node = findByIdAndRoadmapId(nodeId, roadmapId);

        node.changeEducation(request.getSubject());

        return NodeResponse.from(node);
    }

}
