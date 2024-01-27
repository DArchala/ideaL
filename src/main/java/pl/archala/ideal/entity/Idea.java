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
    private String content;

    @Column
    private IdeaCategory category;

    @Column
    private LocalDateTime created;

    @OneToMany(mappedBy = "idea")
    private List<Comment> comments;

    @OneToMany(mappedBy = "idea")
    private List<Realization> realizations;

}
