package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.archala.ideal.enums.IdeaCategory;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private IdeaCategory category;

    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private List<Realization> realizations;

}
