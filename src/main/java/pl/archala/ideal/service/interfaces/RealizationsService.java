package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;

import java.util.List;

public interface RealizationsService {

    GetRealizationDTO findById(Long id);

    GetRealizationDTO save(AddRealizationDTO addRealizationDTO);

    List<GetRealizationDTO> findAllByIdeaId(Long ideaId);
}
