package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.utils.IdealLocalDateTime;

@Component
public class RealizationMapper {

    public Realization toEntity(AddRealizationDTO realizationDTO) {
        Realization realization = new Realization();
        realization.setContent(realizationDTO.content());
        realization.setCreated(IdealLocalDateTime.now());
        return realization;
    }

    public GetRealizationDTO toGetDto(Realization realization) {
        return new GetRealizationDTO(realization.getId(), realization.getContent(), realization.getCreated());
    }
}
