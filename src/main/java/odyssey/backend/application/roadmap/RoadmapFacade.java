package odyssey.backend.application.roadmap;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.directory.DirectoryService;
import odyssey.backend.application.team.TeamService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.directory.Directory;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
import odyssey.backend.domain.team.Team;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.presentation.roadmap.dto.request.RoadmapRequest;
import odyssey.backend.presentation.roadmap.dto.response.PersonalRoadmapResponse;
import odyssey.backend.presentation.roadmap.dto.response.TeamRoadmapResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RoadmapFacade {

    private final RoadmapRepository roadmapRepository;
    private final DirectoryService directoryService;
    private final TeamService teamService;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public PersonalRoadmapResponse savePersonalRoadmap(RoadmapRequest request, User user){

        Directory directory = directoryService.findDirectoryById(request.getDirectoryId());

        Roadmap roadmap = roadmapRepository.save(
                Roadmap.from(request, directory, user, null)
        );

        return PersonalRoadmapResponse.from(roadmap, user.getUuid());
    }

    @Transactional
    public TeamRoadmapResponse saveTeamRoadmap(RoadmapRequest request, User user, Long teamId){
        Team team = teamService.findByTeamId(teamId);

        Directory directory = directoryService.findDirectoryById(request.getDirectoryId());

        TeamRoadmapResponse response = TeamRoadmapResponse.from(roadmapRepository.save(
                Roadmap.from(request, directory, user, team)), user.getUuid());

        messagingTemplate.convertAndSend("/topic/roadmap/team/" + teamId + "/created", response);

        return response;
    }

    @Transactional
    public void deleteRoadmapById(Long id) {
        Roadmap roadmap = findRoadmapById(id);

        if (roadmap.getTeam() != null) {
            Long teamId = roadmap.getTeam().getId();
            roadmapRepository.deleteById(id);
            messagingTemplate.convertAndSend("/topic/roadmap/team/" + teamId + "/deleted", id);
        } else {
            roadmapRepository.deleteById(id);
        }
    }

    private Roadmap findRoadmapById(Long id) {
        return roadmapRepository.findById(id)
                .orElseThrow(RoadmapNotFoundException::new);
    }

    @Transactional
    public PersonalRoadmapResponse update(Long id, RoadmapRequest request, User user) {
        Roadmap roadmap = findRoadmapById(id);

        Directory directory = directoryService.findDirectoryById(request.getDirectoryId());

        if(!roadmap.getDirectory().getId().equals(directory.getId())) {
            roadmap.changeDirectory(directory);
            roadmap.updateLastModifiedAt();
        }

        roadmap.update(request.getTitle(), request.getDescription(), request.getColor(), request.getIcon());

        PersonalRoadmapResponse response = PersonalRoadmapResponse.from(roadmap, user.getUuid());

        if (roadmap.getTeam() != null) {
            Long teamId = roadmap.getTeam().getId();
            TeamRoadmapResponse teamResponse = TeamRoadmapResponse.from(roadmap, user.getUuid());
            messagingTemplate.convertAndSend("/topic/roadmap/team/" + teamId + "/updated", teamResponse);
        }

        return response;
    }

    @Transactional
    public PersonalRoadmapResponse toggleFavorite(Long id, User user) {
        Roadmap roadmap = findRoadmapById(id);

        roadmap.toggleFavorite();

        return PersonalRoadmapResponse.from(roadmap, user.getUuid());
    }

}
