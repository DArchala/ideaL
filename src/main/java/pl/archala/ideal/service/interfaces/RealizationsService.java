package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.realization.GetRealizationDTO;

public interface RealizationsService {

    GetRealizationDTO findById(Long id);
}
