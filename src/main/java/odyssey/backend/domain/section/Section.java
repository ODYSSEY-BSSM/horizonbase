package odyssey.backend.domain.section;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.text.Text;
import odyssey.backend.presentation.section.dto.request.SectionRequest;
import odyssey.backend.presentation.section.dto.request.UpdateSectionRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer x;

    @Column(nullable = false)
    private Integer y;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer height;

    @ManyToOne(fetch = FetchType.LAZY)
    private Roadmap roadmap;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Text> texts = new ArrayList<>();

    public static Section from(SectionRequest request, Roadmap roadmap) {
        return new Section(
                request.getName(),
                request.getX(),
                request.getY(),
                request.getWidth(),
                request.getHeight(),
                roadmap
        );
    }

    Section(String name, Integer x, Integer y, Integer width, Integer height, Roadmap roadmap) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roadmap = roadmap;
    }

    public void updateSection(UpdateSectionRequest request, List<Node> nodes){
        this.name = request.getName();
        this.x = request.getX();
        this.y = request.getY();
        this.width = request.getWidth();
        this.height = request.getHeight();
        addNode(nodes);
    }

    public void addNode(List<Node> nodes){
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        for (Node node : nodes) {
            node.setSection(this);
            this.nodes.add(node);
        }
    }

}
