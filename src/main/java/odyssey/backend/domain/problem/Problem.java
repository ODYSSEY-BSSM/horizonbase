package odyssey.backend.domain.problem;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.presentation.problem.dto.request.ProblemRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "tbl_problem_choice",
            joinColumns = @JoinColumn(name = "problem_id"))
    private List<String> choices = new ArrayList<>();

    @Column(nullable = false)
    private Integer correct;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

    Problem(String title, List<String> choices, Integer correct, Node node){
        this.title = title;
        this.choices = choices;
        this.correct = correct;
        this.node = node;
        status = Status.UNRESOLVED;
    }

    public static Problem from(ProblemRequest request, Node node){
        return new Problem(
                request.getTitle(),
                request.getChoices(),
                request.getCorrect(),
                node
        );
    }

    public boolean isCorrect(Integer correct){
        if(this.correct.equals(correct)){
            this.status = Status.RESOLVED;
            return true;
        }
        this.status = Status.FAILED;
        return false;
    }

    public boolean isResolved(){
        return this.status == Status.RESOLVED;
    }

}
