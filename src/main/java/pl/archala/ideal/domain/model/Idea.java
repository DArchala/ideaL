package pl.archala.ideal.domain.model;

import jakarta.persistence.*;
import lombok.*;
import pl.archala.ideal.domain.enums.IdeaCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private IdeaCategory category;

    private OffsetDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "idea")
    @ToString.Exclude
    private List<Realization> realizations;

    public void addRealization(Realization realization) {
        realizations.add(realization);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Idea idea)) {
            return false;
        }
        return Objects.equals(title, idea.title) && Objects.equals(content, idea.content) && category == idea.category &&
               Objects.equals(createdAt, idea.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, category, createdAt);
    }
}
