package odyssey.backend.domain.roadmap.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Category {

    @Column(name = "category_name", nullable = false)
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
