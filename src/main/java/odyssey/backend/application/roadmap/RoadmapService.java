    package odyssey.backend.application.roadmap;

    import lombok.RequiredArgsConstructor;
    import odyssey.backend.domain.auth.User;
    import odyssey.backend.domain.directory.Directory;
    import odyssey.backend.domain.directory.exception.DirectoryNotFoundException;
    import odyssey.backend.domain.roadmap.Roadmap;
    import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
    import odyssey.backend.infrastructure.persistence.directory.DirectoryRepository;
    import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
    import odyssey.backend.presentation.roadmap.dto.response.CountResponse;
    import odyssey.backend.presentation.roadmap.dto.response.PersonalRoadmapResponse;
    import odyssey.backend.presentation.roadmap.dto.response.RoadmapResponse;
    import odyssey.backend.presentation.roadmap.dto.response.TeamRoadmapResponse;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class RoadmapService {

        private final RoadmapRepository roadmapRepository;
        private final DirectoryRepository directoryRepository;


        public List<PersonalRoadmapResponse> findPersonalRoadmaps(User user, Long directoryId) {
            Directory directory = directoryRepository.findById(directoryId)
                    .orElseThrow(DirectoryNotFoundException::new);

            return roadmapRepository.findByUserAndTeamIsNullAndDirectoryOrderByLastAccessedAtDesc(user, directory)
                    .stream()
                    .map(roadmap -> PersonalRoadmapResponse.from(roadmap, user.getUuid()))
                    .toList();
        }

        public List<TeamRoadmapResponse> findTeamRoadmaps(User user, Long teamId) {
            return roadmapRepository.findByTeam_IdOrderByLastAccessedAtDesc(teamId)
                    .stream()
                    .map(roadmap -> TeamRoadmapResponse.from(roadmap, user.getUuid()))
                    .toList();
        }

        public PersonalRoadmapResponse getLastAccessedRoadmap(User user) {

            Roadmap roadmap = roadmapRepository.findTopByUserOrderByLastAccessedAtDesc(user)
                    .orElseThrow(RoadmapNotFoundException::new);

            return PersonalRoadmapResponse.from(roadmap, user.getUuid());
        }

        public CountResponse getRoadmapCount(User user) {
            Long count = roadmapRepository.countByUser(user);

            return CountResponse.from(count);
        }

        public CountResponse getTeamRoadmapCount(User user) {
            return CountResponse.from(
                    roadmapRepository.countByUserAndTeamIsNotNull(user)
            );
        }

        public CountResponse getTeamCount(User user){
            return CountResponse.from(
                    roadmapRepository.countTeamsByUser(user)
            );
        }

        public PersonalRoadmapResponse getPersonalRoadmap(Long roadmapId, User user) {
            Roadmap roadmap = roadmapRepository.findById(roadmapId)
                    .orElseThrow(RoadmapNotFoundException::new);

            return PersonalRoadmapResponse.from(roadmap, user.getUuid());
        }

        public List<RoadmapResponse> getRoadmaps(User user) {
            List<Roadmap> roadmaps = roadmapRepository.findByUserOrTeamIn(user, user.getTeams());
            return roadmaps.stream()
                    .map(RoadmapResponse::from)
                    .toList();
        }


    }
