package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Realization;

@Component
@RequiredArgsConstructor
public class RealizationMapper {

    private final ApplicationTime applicationTime;

    public Realization toEntity(AddRealizationDTO realizationDTO) {
        Realization realization = new Realization();
        realization.setContent(realizationDTO.content());
        realization.setCreatedAt(applicationTime.now());
        return realization;
    }

    public GetRealizationDTO toGetDto(Realization realization) {
        return new GetRealizationDTO(realization.getId(), realization.getContent(), realization.getCreatedAt());
    }
}
