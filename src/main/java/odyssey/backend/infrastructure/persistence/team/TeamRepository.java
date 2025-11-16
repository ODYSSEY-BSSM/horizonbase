package odyssey.backend.infrastructure.persistence.team;

import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByLeader(User leader);
    Optional<Team> findByInviteCode(String inviteCode);
    List<Team> findTeamsByLeader(User leader);
    List<Team> findTeamsByMembersContaining(User user);
}
