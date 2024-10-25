package pl.archala.ideal.application.rest.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCommentRequest(@NotNull Long id, String content) {
}
