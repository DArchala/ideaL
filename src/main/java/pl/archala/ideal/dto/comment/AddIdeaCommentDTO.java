package pl.archala.ideal.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddIdeaCommentDTO(
        @NotBlank(message = "Comment content must not be blank") String content,
        @NotNull Long ideaId) {
}
