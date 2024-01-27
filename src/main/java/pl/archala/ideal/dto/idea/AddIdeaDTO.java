package pl.archala.ideal.dto.idea;

import jakarta.validation.constraints.NotBlank;

public record AddIdeaDTO(
        @NotBlank(message = "Idea title must not be blank") String title,
        @NotBlank(message = "Idea content must not be blank") String content) {
}