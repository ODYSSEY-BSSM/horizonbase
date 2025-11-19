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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .sorted(Comparator.comparing(Node::getId))
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

        List<Node> result = new ArrayList<>(nodes);
        Map<Long, Node> resultMap = result.stream()
                .collect(Collectors.toMap(Node::getId, n -> n));

        aiResponse.nodes().forEach(vo -> {
            Long nodeId = vo.id();
            Long parentId = vo.parentNodeId();
            if (parentId != null) {
                Node child = resultMap.get(nodeId);
                Node parent = resultMap.get(parentId);
                if (child != null && parent != null) {
                    child.setParent(parent);
                }
            }
        });

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

        Map<Long, AiModifyNodeResponse> responseMap = aiResponse.nodes()
                .stream()
                .collect(Collectors.toMap(AiModifyNodeResponse::id, vo -> vo));

        existNodes
                .forEach(node -> {
                    AiModifyNodeResponse info = responseMap.get(node.getId());

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

        List<Node> newNodes = aiResponse.nodes()
                .stream()
                .filter(vo -> !nodeRepository.existsById(vo.id()))
                .map(response -> Node.from(
                            new NodeRequest(
                                    response.title(),
                                    response.description(),
                                    100,
                                    100,
                                    response.type(),
                                    response.x(),
                                    response.y(),
                                    Color.BLUE,
                                    response.parentId()
                            ),
                            roadmap,
                            null
                ))
                .toList();

        nodeRepository.saveAll(newNodes);

        List<Node> result = new ArrayList<>();
        result.addAll(newNodes);
        result.addAll(existNodes);

        Map<Long, Node> resultMap = result.stream()
                .collect(Collectors.toMap(Node::getId, n -> n));

        aiResponse.nodes().forEach(vo -> {
            Long nodeId = vo.id();
            Long parentId = vo.parentId();

            if (parentId != null) {
                Node child = resultMap.get(nodeId);
                Node parent = resultMap.get(parentId);

                if (child != null && parent != null) {
                    child.setParent(parent);
                }
            }
        });


        return result
                .stream()
                .sorted(Comparator.comparing(Node::getId))
                .map(SimpleNodeResponse::from)
                .toList();

    }

}
