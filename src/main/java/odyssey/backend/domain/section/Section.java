package odyssey.backend.domain.section;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.presentation.section.dto.request.SectionRequest;

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

    private String name;

    private Integer x;

    private Integer y;

    private Integer width;

    private Integer height;

    @ManyToOne(fetch = FetchType.LAZY)
    private Roadmap roadmap;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Node> nodes = new ArrayList<>();

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

    public void addNode(List<Node> nodes){
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        if (this.nodes == null) {
            this.nodes = new ArrayList<>();
        }
        this.nodes.addAll(nodes);
    }

}
