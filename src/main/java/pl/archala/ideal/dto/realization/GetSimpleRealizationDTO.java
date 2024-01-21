package pl.archala.ideal.dto.realization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;

/**
 * Simple Realization dto, without any related entities data
 */

@Getter
@AllArgsConstructor
public class GetSimpleRealizationDTO {

    private final Long id;
    private final String content;
    private final LocalDateTime created;

    public GetSimpleRealizationDTO(Realization realization) {
        this.id = realization.getId();
        this.content = realization.getContent();
        this.created = realization.getCreated();
    }
}
