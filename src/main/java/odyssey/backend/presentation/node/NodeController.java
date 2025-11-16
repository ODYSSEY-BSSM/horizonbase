package odyssey.backend.presentation.node;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.node.NodeService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.node.dto.request.SubjectRequest;
import odyssey.backend.presentation.node.dto.response.NodeResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.ListCommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmap/{roadmapId}/nodes")
public class NodeController {

    private final NodeService nodeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleCommonResponse<NodeResponse> createNode(
            @PathVariable Long roadmapId,
            @Valid @RequestBody NodeRequest nodeRequest,
            @AuthenticationPrincipal User user) {
        return CommonResponse.ok(nodeService.createNode(roadmapId, nodeRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListCommonResponse<NodeResponse> getNodesByRoadmap(
            @PathVariable Long roadmapId,
            @AuthenticationPrincipal User user) {
        return CommonResponse.ok(nodeService.getNodesByRoadmapId(roadmapId));
    }

    @GetMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<NodeResponse> getNode(
            @PathVariable Long nodeId,
            @PathVariable Long roadmapId,
            @AuthenticationPrincipal User user) {
        return CommonResponse.ok(nodeService.getNodeByIdAndRoadmapId(nodeId, roadmapId));
    }

    @PutMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<NodeResponse> updateNode(
            @PathVariable Long nodeId,
            @PathVariable Long roadmapId,
            @Valid @RequestBody NodeRequest request,
            @AuthenticationPrincipal User user) {
        return CommonResponse.ok(nodeService.updateNode(nodeId, roadmapId, request));
    }

    @DeleteMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNode(
            @PathVariable Long nodeId,
            @PathVariable Long roadmapId,
            @AuthenticationPrincipal User user) {
        nodeService.deleteNodeByIdAndRoadmapId(nodeId, roadmapId);
    }

    @PatchMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<NodeResponse> updateEducation(
            @PathVariable Long nodeId,
            @PathVariable Long roadmapId,
            @Valid @RequestBody SubjectRequest request,
            @AuthenticationPrincipal User user) {
        return CommonResponse.ok(nodeService.changeEducation(nodeId, roadmapId, request));
    }

    @PostMapping("/ai")
    @ResponseStatus(HttpStatus.CREATED)
    public ListCommonResponse<NodeResponse> createAiNodes(
            @PathVariable Long roadmapId,
            @RequestBody @Valid GenerateRoadmapRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(nodeService.generageAiNodes(roadmapId, request));
    }

}
