package odyssey.backend.application.node;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.directory.exception.DirectoryNotFoundException;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.roadmap.Icon;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.team.Team;
import odyssey.backend.domain.team.exception.TeamNotFoundException;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.infrastructure.persistence.directory.DirectoryRepository;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.infrastructure.persistence.team.TeamRepository;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.ai.dto.response.AiNodeListResponse;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.node.dto.response.AiRoadmapResponse;
import odyssey.backend.presentation.roadmap.dto.request.RoadmapRequest;
import odyssey.backend.shared.color.Color;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenerateAiRoadmapUseCase {

    private final NodeRepository nodeRepository;
    private final DirectoryRepository directoryRepository;
    private final TeamRepository teamRepository;
    private final RoadmapRepository roadmapRepository;
    private final AiService aiService;

    @Transactional
    public AiRoadmapResponse generageAiNodes(Long directoryId, Long teamId, GenerateRoadmapRequest request, User user){
        Team team = null;
        if (teamId != null) {
            team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        }

        Roadmap roadmap = roadmapRepository.save(Roadmap.from(
                new RoadmapRequest(
                        "Ai 로드맵",
                        "AI로 생성된 로드맵입니다.",
                        directoryId,
                        Color.BLUE,
                        Icon.CODE),
                directoryRepository.findById(directoryId)
                        .orElseThrow(DirectoryNotFoundException::new),
                user,
                team
        ));


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

        return AiRoadmapResponse.from(
                roadmap,
                result
        );
    }

}
