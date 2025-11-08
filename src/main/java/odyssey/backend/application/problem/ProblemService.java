package odyssey.backend.application.problem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.node.exception.NodeNotFoundException;
import odyssey.backend.domain.problem.Problem;
import odyssey.backend.domain.problem.exception.ProblemNotFoundException;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.problem.ProblemRepository;
import odyssey.backend.presentation.problem.dto.request.ProblemRequest;
import odyssey.backend.presentation.problem.dto.request.SolveProblemRequest;
import odyssey.backend.presentation.problem.dto.response.ProblemResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final NodeRepository nodeRepository;

    @Transactional
    public ProblemResponse solveProblem(Long id, SolveProblemRequest request) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(ProblemNotFoundException::new);

        Node node = problem.getNode();

        String answer = request.getAnswer();

        solve(answer, problem, node);

        return ProblemResponse.from(problem);
    }

    @Transactional
    public ProblemResponse createProblem(ProblemRequest request, Long nodeId){
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(NodeNotFoundException::new);

        node.validate();

        Problem problem = problemRepository.save(Problem.from(request, node));

        return ProblemResponse.from(problem);
    }

    private void solve(String answer, Problem problem, Node node){
        if(problem.isCorrect(answer)){
            node.solveProblem(problem, answer);
        }
    }

}
