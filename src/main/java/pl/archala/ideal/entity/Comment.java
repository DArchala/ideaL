package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "idea_id")
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "realization_id")
    private Realization realization;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    public Optional<Idea> getIdea() {
        return Optional.ofNullable(idea);
    }

    public Optional<Realization> getRealization() {
        return Optional.ofNullable(realization);
    }

    public Optional<Comment> getParentComment() {
        return Optional.ofNullable(parentComment);
    }

    public Optional<List<Comment>> getOptionalComments() {
        return Optional.ofNullable(comments);
    }
}
