package odyssey.backend.domain.text;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.section.Section;
import odyssey.backend.presentation.text.dto.request.TextRequest;
import odyssey.backend.presentation.text.dto.request.UpdateTextRequest;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_text")
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Roadmap roadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

    @Enumerated(EnumType.STRING)
    private Type type;

    public static Text from(TextRequest request, Roadmap roadmap){
        return new Text(
                request.getText(),
                request.getType(),
                roadmap
        );
    }

    Text(String text, Type type, Roadmap roadmap){
        this.text = text;
        this.type = type;
        this.roadmap = roadmap;
    }

    private void setRoadmap(Roadmap roadmap){
        if(roadmap == null){
            return;
        }
        this.roadmap = roadmap;
    }

    public void setSection(Section section) {
        if (this.section != null) {
            this.section.getTexts().remove(this);
        }

        this.section = section;

        if (section != null && !section.getTexts().contains(this)) {
            section.getTexts().add(this);
        }
    }

    public Long getRoadmapId(){
        return roadmap.getId();
    }

    public void update(UpdateTextRequest request, Section section){
        this.text = request.getText();
        this.type = request.getType();
        setSection(section);
    }

}
