package pl.archala.ideal.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetCommentDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    private final GetIdeaDTO ideaDTO;

    public GetCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
        this.ideaDTO = new GetIdeaDTO(comment.getIdea());
    }
}
