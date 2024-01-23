package pl.archala.ideal.dto.realization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetRealizationDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;
    private final Long ideaId;

    public GetRealizationDTO(Realization realization) {
        this.id = realization.getId();
        this.content = realization.getContent();
        this.created = realization.getCreated();
        this.ideaId = realization.getIdea().getId();
    }
}
