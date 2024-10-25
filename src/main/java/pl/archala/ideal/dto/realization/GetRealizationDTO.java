package pl.archala.ideal.dto.realization;

import java.time.OffsetDateTime;

public record GetRealizationDTO(Long id, String content, OffsetDateTime created) {

}
