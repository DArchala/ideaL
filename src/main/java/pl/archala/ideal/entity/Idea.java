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

    @Column
    private String title;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    private IdeaCategory category;

    @Column
    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany
    private List<Realization> realizations;

}
