package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.archala.ideal.enums.IdeaCategory;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@Entity
@Table(name = "ideas")
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private List<Realization> realizations;

}
