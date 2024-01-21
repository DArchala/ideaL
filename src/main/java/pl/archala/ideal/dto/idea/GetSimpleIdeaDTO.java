package pl.archala.ideal.dto.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetSimpleIdeaDTO {
    private final Long id;
    private final String name;
    private final String content;
    private final LocalDateTime created;

    public GetSimpleIdeaDTO(Idea idea) {
        this.id = idea.getId();
        this.name = idea.getName();
        this.content = idea.getContent();
        this.created = idea.getCreated();
    }
}
