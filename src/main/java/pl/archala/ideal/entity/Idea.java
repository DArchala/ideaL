package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.archala.ideal.enums.IdeaCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Data
@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private IdeaCategory category;

    private LocalDateTime created;

    @OneToMany
    private List<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private List<Realization> realizations;

}
