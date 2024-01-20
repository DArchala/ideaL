package pl.archala.ideal.dto.idea;

import lombok.Getter;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;

@Getter
public class AddIdeaDTO {
    private final String name;
    private final String content;
    private final LocalDateTime create;

    public AddIdeaDTO(Idea idea) {
        this.name = idea.getName();
        this.content = idea.getName();
        this.create = idea.getCreated();
    }
}