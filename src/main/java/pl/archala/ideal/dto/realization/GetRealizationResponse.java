package pl.archala.ideal.dto.realization;

import java.time.OffsetDateTime;

public record GetRealizationResponse(Long id, String content, OffsetDateTime createdAt) {

}
