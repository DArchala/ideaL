package pl.archala.ideal.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetSimpleCommentDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;

    public GetSimpleCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.created = comment.getCreated();
    }
}
