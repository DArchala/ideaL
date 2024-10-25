package pl.archala.ideal.application.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.application.rest.dto.in.SaveRealizationRequest;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.domain.model.Realization;
import pl.archala.ideal.infrastructure.component.ApplicationTime;

@Component
@RequiredArgsConstructor
public class RealizationMapper {

    private final ApplicationTime applicationTime;

    public Realization toEntity(SaveRealizationRequest realizationDTO) {
        return Realization.builder()
                          .content(realizationDTO.content())
                          .createdAt(applicationTime.now())
                          .build();
    }

    public GetRealizationResponse toGetRealizationResponse(Realization realization) {
        return new GetRealizationResponse(realization.getId(), realization.getContent(), realization.getCreatedAt());
    }
}
