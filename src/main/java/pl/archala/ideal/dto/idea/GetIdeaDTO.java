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
    private final String title;
    private final String content;
    private final LocalDateTime created;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> commentsIds;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> realizationsIds;

    public GetIdeaDTO(Idea idea) {
        this.id = idea.getId();
        this.title = idea.getTitle();
        this.content = idea.getContent();
        this.created = idea.getCreated();
        idea.getOptionalComments().ifPresent(c -> this.commentsIds = c.stream().map(Comment::getId).toList());
        idea.getRealizations().ifPresent(r -> this.realizationsIds = r.stream().map(Realization::getId).toList());
    }
}
