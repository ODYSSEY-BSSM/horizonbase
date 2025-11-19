package odyssey.backend.application.node;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.node.exception.NodeNotFoundException;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.ai.dto.request.ModifyNodeRequest;
import odyssey.backend.presentation.ai.dto.response.AiNodeListResponse;
import odyssey.backend.presentation.ai.dto.response.ModifyNodeResponse;
import odyssey.backend.presentation.ai.dto.response.vo.AiModifyNodeResponse;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.node.dto.request.SubjectRequest;
import odyssey.backend.presentation.node.dto.response.NodeResponse;
import odyssey.backend.presentation.node.dto.response.SimpleNodeResponse;
import odyssey.backend.shared.color.Color;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final RoadmapRepository roadmapRepository;
    private final NodeRepository nodeRepository;
    private final AiService aiService;
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
                request.getColor());

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

    @Transactional
    public List<NodeResponse> generageAiNodes(Long roadmapId, GenerateRoadmapRequest request){
        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        AiNodeListResponse aiResponse = aiService.generateRoadmap(request);

        List<Node> nodes = aiResponse.nodes()
                .stream()
                .map(aiNodeResponse ->
                    Node.from(
                            new NodeRequest(
                                    aiNodeResponse.title(),
                                    aiNodeResponse.description(),
                                    100,
                                    100,
                                    aiNodeResponse.type(),
                                    aiNodeResponse.x(),
                                    aiNodeResponse.y(),
                                    Color.BLUE,
                                    aiNodeResponse.parentNodeId()
                            ),
                            roadmap,
                            null
                    )
                )
                .toList();

        nodeRepository.saveAll(nodes);

        for (int i = 0; i < nodes.size(); i++){
            Node node = nodes.get(i);
            Long parentId = aiResponse.nodes().get(i).parentNodeId();
            if (parentId != null){
                Node parent = nodeRepository.findById(parentId)
                        .orElseThrow(NodeNotFoundException::new);
                node.setParent(parent);
            }
        }

        return nodes.stream()
                .map(NodeResponse::from)
                .toList();
    }

    @Transactional
    public List<SimpleNodeResponse> modifyNodeByAi(Long roadmapId, ModifyNodeRequest request){
        Roadmap roadmap = getRoadmapById(roadmapId);

        roadmap.updateLastModifiedAt();

        ModifyNodeResponse aiResponse = aiService.modifyNode(request);

        List<Long> existNodeId = aiResponse.nodes()
                .stream()
                .map(AiModifyNodeResponse::id)
                .toList();

        List<Node> existNodes = nodeRepository.findAllByIdInAndRoadmapId(existNodeId, roadmapId);

        existNodes
                .forEach(node -> {
                    AiModifyNodeResponse info = aiResponse.nodes()
                            .stream()
                            .filter(vo -> vo.id().equals(node.getId()))
                            .findFirst()
                            .get();

                    node.update(
                            info.title(),
                            info.description(),
                            node.getHeight(),
                            node.getWidth(),
                            info.type(),
                            info.x(),
                            info.y(),
                            node.getColor()
                    );
                });

        return existNodes
                .stream()
                .map(SimpleNodeResponse::from)
                .toList();

    }

}
