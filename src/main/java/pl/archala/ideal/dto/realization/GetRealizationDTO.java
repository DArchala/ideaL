package pl.archala.ideal.dto.realization;

import java.time.LocalDateTime;

public record GetRealizationDTO(Long id, String content, LocalDateTime created) {

}
