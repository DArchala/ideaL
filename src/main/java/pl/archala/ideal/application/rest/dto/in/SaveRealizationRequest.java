package pl.archala.ideal.application.rest.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveRealizationRequest(
        @NotBlank(message = "Realization content must not be blank") String content,
        @NotNull Long ideaId) {
}
