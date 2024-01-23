package pl.archala.ideal.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetCommentDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    private int commentsCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long ideaId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long parentCommentId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long realizationId;

    public GetCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
        if (comment.getIdea() != null) {
            this.ideaId = comment.getIdea().getId();
        }
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getId();
        }
        if (comment.getRealization() != null) {
            this.realizationId = comment.getRealization().getId();
        }
        if (comment.getComments() != null) {
            this.commentsCount = comment.getComments().size();
        }

    }

}
