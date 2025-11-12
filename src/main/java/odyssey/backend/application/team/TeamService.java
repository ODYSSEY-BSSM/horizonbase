package odyssey.backend.application.team;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.UserNotFoundException;
import odyssey.backend.domain.team.Team;
import odyssey.backend.domain.team.exception.TeamNotFoundException;
import odyssey.backend.infrastructure.persistence.auth.UserRepository;
import odyssey.backend.infrastructure.persistence.team.TeamRepository;
import odyssey.backend.presentation.team.dto.request.TeamRequest;
import odyssey.backend.presentation.team.dto.response.TeamListResponse;
import odyssey.backend.presentation.team.dto.response.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamResponse create(TeamRequest request, User leader){
        return TeamResponse.from(teamRepository.save(
                Team.ok(request, leader)
        ));
    }

    public void delete(Long id, User user){
        Team team = findByTeamId(id);

        team.validateLeader(user);

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
        Team team = findByTeamId(teamId);
        
        return team.getMembers().stream()
                .map(User::getUuid)
                .anyMatch(userId::equals);
    }

    @Transactional
    public TeamListResponse teamList(User user){
        User findTeamUser = userRepository.findById(user.getUuid())
                .orElseThrow(UserNotFoundException::new);
        return TeamListResponse.from(findTeamUser);
    }

}
