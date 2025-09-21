package odyssey.backend.application.team;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;
import odyssey.backend.domain.team.TeamApply;
import odyssey.backend.domain.team.exception.ApplyNotFoundException;
import odyssey.backend.domain.team.exception.TeamNotFoundException;
import odyssey.backend.infrastructure.persistence.team.TeamApplyRepository;
import odyssey.backend.infrastructure.persistence.team.TeamRepository;
import odyssey.backend.presentation.team.dto.response.ApplyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamApplyService {

    private final TeamRepository teamRepository;
    private final TeamApplyRepository teamApplyRepository;

    public ApplyResponse apply(Long teamId, User user){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        return ApplyResponse.of(teamApplyRepository.save(new TeamApply(team, user)));
    }

    @Transactional
    public ApplyResponse approve(Long applyId, User user){
        TeamApply apply = findById(applyId);

        Team team = apply.getTeam();

        validateLeader(team, user);

        team.addMember(apply.getUser());

        return ApplyResponse.of(teamApplyRepository.save(apply));
    }

    @Transactional
    public void reject(Long applyId, User user){
        TeamApply apply = findById(applyId);

        Team team = apply.getTeam();

        validateLeader(team, user);

        teamApplyRepository.deleteById(applyId);
    }

    private void validateLeader(Team team, User user){
        team.validateLeader(user);
    }

    private TeamApply findById(Long applyId){
        return teamApplyRepository.findById(applyId)
                .orElseThrow(ApplyNotFoundException::new);
    }

}
