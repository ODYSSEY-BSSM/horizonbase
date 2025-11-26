package odyssey.backend.presentation.roadmap;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.roadmap.RoadmapFacade;
import odyssey.backend.application.roadmap.RoadmapService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.roadmap.dto.request.RoadmapRequest;
import odyssey.backend.presentation.roadmap.dto.response.TeamRoadmapListResponse;
import odyssey.backend.presentation.roadmap.dto.response.TeamRoadmapResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.ListCommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams/{teamId}/roadmap")
@RequiredArgsConstructor
public class TeamRoadmapController {

    private final RoadmapFacade roadmapFacade;
    private final RoadmapService roadmapService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleCommonResponse<TeamRoadmapResponse> createTeamRoadmap(
            @PathVariable("teamId") Long teamId,
            @RequestBody @Valid RoadmapRequest request,
            @AuthenticationPrincipal User user
    ) {
        return CommonResponse.ok(roadmapFacade.saveTeamRoadmap(request, user, teamId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListCommonResponse<TeamRoadmapResponse> getTeamRoadmaps(
            @AuthenticationPrincipal User user,
            @PathVariable("teamId") Long teamId
    ){
        return CommonResponse.ok(roadmapService.findTeamRoadmaps(user, teamId));
    }

    @GetMapping("/directory-id")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<TeamRoadmapListResponse> getTeamRoadmapsByDirectory(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "directoryId") Long directoryId,
            @PathVariable("teamId") Long teamId
    ){
        return CommonResponse.ok(roadmapService.getTeamRoadmapsByDirectory(user, directoryId));
    }

}
