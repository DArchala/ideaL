package pl.archala.ideal.dto.idea;

import jakarta.validation.constraints.NotBlank;

public record AddIdeaDTO(
        @NotBlank(message = "Idea name must not be blank") String name,
        @NotBlank(message = "Idea content must not be blank") String content) {
}