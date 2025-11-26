package odyssey.backend.infrastructure.persistence.roadmap;

import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.directory.Directory;
import odyssey.backend.domain.node.Subject;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    Optional<Roadmap> findTopByUserOrderByLastAccessedAtDesc(User user);
    List<Roadmap> findByUserAndTeamIsNullAndDirectoryOrderByLastAccessedAtDesc(User user, Directory directory);
    List<Roadmap> findByTeam_IdOrderByLastAccessedAtDesc(Long teamId);
    List<Roadmap> findByUserOrTeamIn(User user, List<Team> teams);
    List<Roadmap> findByUserAndDirectory(User user, Directory directory);
    List<Roadmap> findDistinctByNodesSubject(Subject subject);
    Long countByUser(User user);
    Long countByUserAndTeamIsNotNull(User user);
    Long countTeamsByUser(User user);
    Long countByNodesIsEducationTrue();
    void deleteByTeam(Team team);
    void deleteByUser(User user);
}
