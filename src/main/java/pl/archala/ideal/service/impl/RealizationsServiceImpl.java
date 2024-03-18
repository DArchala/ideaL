package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.RealizationMapper;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealizationsServiceImpl implements RealizationsService {

    private final RealizationsRepository realizationsRepo;
    private final IdeasRepository ideasRepo;
    private final RealizationMapper realizationMapper;

    @Override
    public GetRealizationDTO findById(Long id) {
        return realizationMapper.toDto(findRealizationById(id));
    }

    @Override
    @Transactional
    public GetRealizationDTO save(AddRealizationDTO addRealizationDTO) {
        Realization realization = realizationMapper.toEntity(addRealizationDTO);
        Idea idea = findIdeaById(addRealizationDTO.ideaId());

        realization.setIdea(idea);
        idea.getRealizations().add(realization);

        return realizationMapper.toDto(realizationsRepo.save(realization));
    }

    private Realization findRealizationById(Long id) {
        Optional<Realization> optionalComment = realizationsRepo.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepo.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }

}
