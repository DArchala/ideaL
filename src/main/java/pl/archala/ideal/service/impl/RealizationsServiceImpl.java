package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealizationsServiceImpl implements RealizationsService {

    private final RealizationsRepository realizationsRepository;

    @Override
    public GetRealizationDTO findById(Long id) {
        Realization realization = findRealizationById(id);
        return new GetRealizationDTO(realization);
    }

    private Realization findRealizationById(Long id) {
        Optional<Realization> realizationOptional = realizationsRepository.findById(id);
        if (realizationOptional.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return realizationOptional.get();
    }
}
