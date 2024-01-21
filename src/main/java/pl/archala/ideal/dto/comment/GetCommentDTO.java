package pl.archala.ideal.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.dto.idea.GetSimpleIdeaDTO;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetCommentDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    private final GetSimpleIdeaDTO idea;

    public GetCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
        this.idea = new GetSimpleIdeaDTO(comment.getIdea());
    }
}
