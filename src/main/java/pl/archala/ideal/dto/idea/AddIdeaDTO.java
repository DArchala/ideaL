package pl.archala.ideal.dto.idea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.archala.ideal.enums.IdeaCategory;

public record AddIdeaDTO(
        @NotBlank(message = "Idea title must not be blank") String title,
        @NotBlank(message = "Idea content must not be blank") String content,
        @NotNull(message = "Idea category must not be null") IdeaCategory category) {
}