package pl.archala.ideal.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCommentRequest(@NotNull Long id, String content) {
}
