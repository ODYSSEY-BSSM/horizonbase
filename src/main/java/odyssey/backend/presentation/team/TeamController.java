package odyssey.backend.presentation.team;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.team.TeamService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.team.dto.request.TeamInviteRequest;
import odyssey.backend.presentation.team.dto.request.TeamRequest;
import odyssey.backend.presentation.team.dto.response.TeamListResponse;
import odyssey.backend.presentation.team.dto.response.TeamResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleCommonResponse<TeamResponse> create(
            @RequestBody @Valid TeamRequest request,
            @AuthenticationPrincipal User user){
        return CommonResponse.ok(teamService.create(request, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        teamService.delete(id, user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<TeamResponse> getTeamInfo(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(teamService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<TeamListResponse> getTeams(
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(teamService.teamList(user));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SingleCommonResponse<TeamListResponse> inviteTeam(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid TeamInviteRequest request
    ){
        return CommonResponse.ok(teamService.inviteTeam(request, user));
    }

}
