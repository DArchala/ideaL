package pl.archala.ideal.domain.model;

import jakarta.persistence.*;
import lombok.*;
import pl.archala.ideal.application.rest.dto.in.UpdateCommentRequest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private OffsetDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Comment update(UpdateCommentRequest updateCommentRequest) {
        this.content = updateCommentRequest.content();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment comment)) {
            return false;
        }
        return Objects.equals(content, comment.content) && Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, createdAt);
    }
}


