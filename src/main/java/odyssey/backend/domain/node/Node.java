package odyssey.backend.domain.node;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.problem.Problem;
import odyssey.backend.domain.problem.exception.CantCreateProblemException;
import odyssey.backend.domain.problem.exception.MaxOfProblemException;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.shared.color.Color;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tbl_node")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id")
    private Long id;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false, length = 1500)
    private String description;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer width;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NodeType type;

    @Column(nullable = false)
    private Integer x;

    @Column(nullable = false)
    private Integer y;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_node_id")
    private Node parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Node> children = new ArrayList<>();

    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Problem> problems = new ArrayList<>();

    @Column(nullable = false)
    private Integer progress = 0;

    @Column(nullable = false)
    private boolean isEducation = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Subject subject;

    public static Node from(NodeRequest request, Roadmap roadmap, Node parent) {
        return new Node(
                request.getTitle(),
                request.getDescription(),
                request.getHeight(),
                request.getWidth(),
                request.getType(),
                request.getX(),
                request.getY(),
                request.getColor(),
                roadmap,
                parent
        );
    }

    public Node(
            String title, String description, int height, int width, NodeType type, int x, int y, Color color, Roadmap roadmap, Node parent) {
        this.title = title;
        this.description = description;
        this.height = height;
        this.width = width;
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = color;
        this.roadmap = roadmap;
        this.parent = parent;
    }

    public void update(
            String title, String description, Integer height, Integer width, NodeType type, Integer x, Integer y, Color color
    ){
        this.title = title;
        this.description = description;
        this.height = height;
        this.width = width;
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    private void updateProgress(){
        int totalProblems = problems.size();
        if(totalProblems == 0) {
            this.progress = 0;
            return;
        }
        long progressCount = problems.stream()
                .filter(Problem::isResolved)
                .count();
        this.progress = (int)Math.round((progressCount / (double) totalProblems) * 100);
    }

    public void solveProblem(Problem problem, String answer){
        if(problem.isCorrect(answer)){
            updateProgress();
            roadmap.updateProgress();
        }
    }

    public void validate(){
        if(this.type != NodeType.BOTTOM){
            throw new CantCreateProblemException();
        }
        if(problems.size() == 3){
            throw new MaxOfProblemException();
        }
    }

    public boolean isNotHaveParent(){
        return this.getParent() == null;
    }

    public int problemCount(){
        return this.problems.size();
    }

    public void changeEducation(Subject subject) {
        this.subject = subject;
        this.isEducation = true;
    }

    public void setParent(Node parent) {
        if(parent == null){
            throw new IllegalArgumentException("null이면 안됩니다");
        }
        if (parent == this) {
            throw new IllegalArgumentException("자기 자신은 부모일 수 없습니다.");
        }
        this.parent = parent;
        parent.children.add(this);
    }

}
