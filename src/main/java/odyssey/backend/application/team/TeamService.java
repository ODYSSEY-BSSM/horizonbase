package odyssey.backend.application.team;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;
import odyssey.backend.domain.team.exception.TeamNotFoundException;
import odyssey.backend.infrastructure.persistence.team.TeamRepository;
import odyssey.backend.presentation.team.dto.request.TeamRequest;
import odyssey.backend.presentation.team.dto.response.TeamResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamResponse create(TeamRequest request, User leader){
        return TeamResponse.from(teamRepository.save(
                Team.ok(request, leader)
        ));
    }

    public void delete(Long id, User user){
        Team team = teamRepository.findByLeader(user)
                .orElseThrow(() -> new IllegalArgumentException("팀은 팀장만 삭제할 수 있읍니다."));

        teamRepository.delete(team);
    }

    public TeamResponse findById(Long teamId) {
        Team team = findByTeamId(teamId);

        return TeamResponse.from(team);
    }

    public Team findByTeamId(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }

    public boolean isUserMemberOfTeam(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            return false;
        }
        
        return team.getMembers().stream()
                   .anyMatch(member -> member.getUuid().equals(userId));
    }

}
