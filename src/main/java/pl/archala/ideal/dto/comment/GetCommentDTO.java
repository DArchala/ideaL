package pl.archala.ideal.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetCommentDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> commentsIds;
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
        comment.getIdea().ifPresent(i -> this.ideaId = i.getId());
        comment.getOptionalComments().ifPresent(c -> this.commentsIds = c.stream().map(Comment::getId).toList());
        comment.getRealization().ifPresent(r -> this.realizationId = r.getId());
        comment.getParentComment().ifPresent(pc -> this.parentCommentId = comment.getId());
    }

}
