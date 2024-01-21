package pl.archala.ideal.dto.realization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddRealizationDTO(
        @NotBlank(message = "Realization content must not be blank") String content,
        @NotNull Long ideaId) {
}
