package pl.archala.ideal.dto.realization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetRealizationDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    private final Long ideaId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> commentsId;

    public GetRealizationDTO(Realization realization) {
        this.id = realization.getId();
        this.content = realization.getContent();
        this.created = realization.getCreated();
        this.ideaId = realization.getIdea().getId();
        realization.getOptionalComments().ifPresent(c -> this.commentsId = c.stream().map(Comment::getId).toList());
    }
}
