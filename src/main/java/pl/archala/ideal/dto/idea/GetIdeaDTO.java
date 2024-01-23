package pl.archala.ideal.dto.idea;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetIdeaDTO {

    private final Long id;
    private final String name;
    private final String content;
    private final LocalDateTime created;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> commentsIds;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> realizationsIds;

    public GetIdeaDTO(Idea idea) {
        this.id = idea.getId();
        this.name = idea.getName();
        this.content = idea.getContent();
        this.created = idea.getCreated();
        if (idea.getComments() != null) {
            this.commentsIds = idea.getComments().stream().map(Comment::getId).toList();
        }
        if (idea.getRealizations() != null) {
            this.realizationsIds = idea.getRealizations().stream().map(Realization::getId).toList();
        }

    }
}
