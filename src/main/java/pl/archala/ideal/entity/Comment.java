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

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    public Optional<List<Comment>> getOptionalComments() {
        return Optional.ofNullable(comments);
    }

}
