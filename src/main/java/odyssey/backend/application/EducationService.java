package odyssey.backend.application;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.node.Subject;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.presentation.education.dto.response.EducationResponse;
import odyssey.backend.presentation.roadmap.dto.response.CountResponse;
import odyssey.backend.presentation.roadmap.dto.response.SecondSimpleRoadmapResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final NodeRepository nodeRepository;
    private final RoadmapRepository roadmapRepository;

    public CountResponse getEducationNodeCount(User user){
        Long count = nodeRepository.countByIsEducationTrue();
        user.validEmail(user.getEmail());
        return CountResponse.from(count);
    }

    public EducationResponse getSubjectList(User user){
        user.validEmail(user.getEmail());
        return EducationResponse.from(List.of(Subject.values()));
    }

    public List<SecondSimpleRoadmapResponse> getRoadmapBySubject(User user, Subject subject){
        user.validEmail(user.getEmail());
        List<Roadmap> roadmaps = roadmapRepository.findDistinctByNodesSubject(subject);

        return roadmaps.stream()
                .map(SecondSimpleRoadmapResponse::from)
                .toList();
    }
}
