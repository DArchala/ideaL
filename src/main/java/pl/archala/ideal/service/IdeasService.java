package pl.archala.ideal.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.repository.IdeasRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeasService {

    private final IdeasRepository ideasRepository;
    private final IdeaMapper ideaMapper;

    public GetIdeaDTO getById(Long id) {
        Optional<Idea> ideaOptional = ideasRepository.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaMapper.toGetDTO(ideaOptional.get());
    }

    public GetIdeaDTO save(AddIdeaDTO ideaDTO) {
        Idea idea = ideaMapper.toEntity(ideaDTO);
        Idea saved = ideasRepository.save(idea);
        return ideaMapper.toGetDTO(saved);
    }

    public List<GetIdeaDTO> getPage(PageRequest pageRequest) {
        Page<Idea> ideasPage = ideasRepository.findAll(pageRequest);
        return ideasPage.map(ideaMapper::toGetDTO).getContent();
    }
}
