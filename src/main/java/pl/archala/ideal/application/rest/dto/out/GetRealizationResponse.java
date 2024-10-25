package pl.archala.ideal.application.rest.dto.out;

import java.time.OffsetDateTime;

public record GetRealizationResponse(Long id, String content, OffsetDateTime createdAt) {

}
