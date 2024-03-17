package pl.archala.ideal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.RealizationMapper;
import pl.archala.ideal.repository.wrapper.IdeasRepositoryWrapper;
import pl.archala.ideal.repository.wrapper.RealizationsRepositoryWrapper;
import pl.archala.ideal.service.interfaces.RealizationsService;

@Service
@RequiredArgsConstructor
public class RealizationsServiceImpl implements RealizationsService {

    private final RealizationsRepositoryWrapper realizationsRepository;
    private final IdeasRepositoryWrapper ideasRepository;
    private final RealizationMapper realizationMapper;

    @Override
    public GetRealizationDTO findById(Long id) {
        return realizationMapper.toDto(realizationsRepository.findById(id));
    }

    @Override
    @Transactional
    public GetRealizationDTO save(AddRealizationDTO addRealizationDTO) {
        Realization realization = realizationMapper.toEntity(addRealizationDTO);
        Idea idea = ideasRepository.findById(addRealizationDTO.ideaId());

        realization.setIdea(idea);
        idea.getRealizations().add(realization);

        return realizationMapper.toDto(realizationsRepository.save(realization));
    }

}
