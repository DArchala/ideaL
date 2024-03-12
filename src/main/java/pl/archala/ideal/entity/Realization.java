package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "realizations")
public class Realization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idea_id")
    private Idea idea;

    @OneToMany(mappedBy = "realization")
    private List<Comment> comments;

    private String content;

    private LocalDateTime created;

    public Optional<List<Comment>> getOptionalComments() {
        return Optional.ofNullable(comments);
    }
}
