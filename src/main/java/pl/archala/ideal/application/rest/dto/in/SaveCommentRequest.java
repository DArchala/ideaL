package pl.archala.ideal.application.rest.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveCommentRequest(
        @NotBlank(message = "Comment content must not be blank") String content,
        @NotNull Long parentId) {
}
