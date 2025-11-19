package odyssey.backend.application.problem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.node.exception.NodeNotFoundException;
import odyssey.backend.domain.problem.Problem;
import odyssey.backend.domain.problem.exception.ProblemNotFoundException;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.problem.ProblemRepository;
import odyssey.backend.presentation.ai.dto.request.GenerateQuizRequest;
import odyssey.backend.presentation.ai.dto.response.QuizResponse;
import odyssey.backend.presentation.problem.dto.request.ProblemRequest;
import odyssey.backend.presentation.problem.dto.request.SolveProblemRequest;
import odyssey.backend.presentation.problem.dto.response.ProblemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class  ProblemService {

    private final ProblemRepository problemRepository;
    private final NodeRepository nodeRepository;
    private final AiService aiService;

    @Transactional
    public ProblemResponse solveProblem(Long id, SolveProblemRequest request) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(ProblemNotFoundException::new);

        Node node = problem.getNode();

        Integer correct = problem.getCorrect();

        solve(correct, problem, node);

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

    private void solve(Integer correct, Problem problem, Node node){
        if(problem.isCorrect(correct)){
            node.solveProblem(problem, correct);
        }
    }

    @Transactional
    public List<ProblemResponse> createProblemByAi(Long nodeId, GenerateQuizRequest request){
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(NodeNotFoundException::new);

        QuizResponse aiResponse = aiService.generateQuiz(request);

        List<Problem> problems = aiResponse.questions()
                .stream()
                .map(
                        vo ->
                                Problem.from(
                                        new ProblemRequest(
                                                vo.title(),
                                                vo.choices(),
                                                vo.correct()
                                        ),
                                        node
                                )
                )
                .toList();

        problems = problemRepository.saveAll(problems);

        return problems
                .stream()
                .map(ProblemResponse::from)
                .toList();
    }

}
