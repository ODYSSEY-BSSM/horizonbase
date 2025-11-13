package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.auth.UserFacade;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;
import odyssey.backend.infrastructure.persistence.auth.UserRepository;
import odyssey.backend.infrastructure.persistence.directory.DirectoryRepository;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.infrastructure.persistence.team.TeamRepository;
import odyssey.backend.presentation.auth.dto.request.DeleteUserRequest;
import odyssey.backend.shared.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserFacade userFacade;
    private final DirectoryRepository directoryRepository;
    private final RoadmapRepository roadmapRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    @Transactional
    public void execute(User user, DeleteUserRequest request) {
        User deleteUser = userFacade.getUserByUuid(user.getUuid());

        deleteTeamsWhereUserIsLeader(user);
        removeUserFromTeams(user);
        deleteUserRoadmaps(user);
        deleteUserDirectories(user);
        userRepository.delete(deleteUser);
    }

    private void deleteTeamsWhereUserIsLeader(User user) {
        List<Team> leaderTeams = teamRepository.findTeamsByLeader(user);

        for (Team team : leaderTeams) {
            directoryRepository.deleteByTeamId(team.getId());
            roadmapRepository.deleteByTeam(team);
            teamRepository.delete(team);
        }
    }

    private void removeUserFromTeams(User user) {
        List<Team> memberTeams = new ArrayList<>(user.getTeams());

        for (Team team : memberTeams) {
            if (!team.isLeader(user)) {
                team.removeMember(user);
                teamRepository.save(team);
            }
        }
    }

    private void deleteUserRoadmaps(User user) {
        roadmapRepository.deleteByUser(user);
    }

    private void deleteUserDirectories(User user) {
        directoryRepository.deleteByUser(user);
    }

    public void valid(String rawPassword, String encodedPassword) {
        passwordUtil.validate(rawPassword, encodedPassword);
    }

}