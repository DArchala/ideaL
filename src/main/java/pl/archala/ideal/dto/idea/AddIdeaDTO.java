package pl.archala.ideal.dto.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddIdeaDTO {
    private final String name;
    private final String content;
}