package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.realization.SaveRealizationRequest;
import pl.archala.ideal.dto.realization.GetRealizationResponse;
import pl.archala.ideal.entity.Realization;

@Component
@RequiredArgsConstructor
public class RealizationMapper {

    private final ApplicationTime applicationTime;

    public Realization toEntity(SaveRealizationRequest realizationDTO) {
        Realization realization = new Realization();
        realization.setContent(realizationDTO.content());
        realization.setCreatedAt(applicationTime.now());
        return realization;
    }

    public GetRealizationResponse toGetDto(Realization realization) {
        return new GetRealizationResponse(realization.getId(), realization.getContent(), realization.getCreatedAt());
    }
}
