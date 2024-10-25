package pl.archala.ideal.application.rest.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.archala.ideal.domain.enums.IdeaCategory;

public record SaveIdeaRequest(
        @NotBlank(message = "Idea title must not be blank") String title,
        @NotBlank(message = "Idea content must not be blank") String content,
        @NotNull(message = "Idea category must not be null") IdeaCategory category) {
}