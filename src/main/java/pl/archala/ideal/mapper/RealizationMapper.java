package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;

@Component
public class RealizationMapper {

    public Realization toEntity(AddRealizationDTO realizationDTO) {
        Realization realization = new Realization();
        realization.setContent(realizationDTO.content());
        realization.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return realization;
    }
}
