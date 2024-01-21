package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.dto.idea.GetSimpleIdeaDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepository ideasRepository;
    private final IdeaMapper ideaMapper;

    public GetIdeaDTO getById(Long id) {
        Idea idea = findIdeaById(id);
        return new GetIdeaDTO(idea);
    }

    public GetSimpleIdeaDTO save(AddIdeaDTO ideaDTO) {
        Idea idea = ideaMapper.toEntity(ideaDTO);
        Idea saved = ideasRepository.save(idea);
        return new GetSimpleIdeaDTO(saved);
    }

    public List<GetSimpleIdeaDTO> getPage(PageRequest pageRequest) {
        Page<Idea> ideasPage = ideasRepository.findAll(pageRequest);
        return ideasPage.map(GetSimpleIdeaDTO::new).getContent();
    }

    @Override
    public GetSimpleIdeaDTO deleteById(Long id) {
        Idea idea = findIdeaById(id);
        ideasRepository.delete(idea);
        return new GetSimpleIdeaDTO(idea);
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepository.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }
}
