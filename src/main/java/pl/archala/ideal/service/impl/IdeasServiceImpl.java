package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepository ideasRepository;
    private final IdeaMapper ideaMapper;
    private final Random random = new Random();

    public GetIdeaDTO findById(Long id) {
        Idea idea = findIdeaById(id);
        return new GetIdeaDTO(idea);
    }

    public GetIdeaDTO save(AddIdeaDTO ideaDTO) {
        Idea idea = ideaMapper.toEntity(ideaDTO);
        Idea saved = ideasRepository.save(idea);
        return new GetIdeaDTO(saved);
    }

    public List<GetIdeaDTO> getPage(PageRequest pageRequest) {
        Page<Idea> ideasPage = ideasRepository.findAll(pageRequest);
        return ideasPage.map(GetIdeaDTO::new).getContent();
    }

    @Override
    public GetIdeaDTO deleteById(Long id) {
        Idea idea = findIdeaById(id);
        ideasRepository.delete(idea);
        return new GetIdeaDTO(idea);
    }

    @Override
    public GetIdeaDTO getRandom(IdeaCategory category) {
        long count = ideasRepository.count();
        if (count == 0) {
            throw new EntityNotFoundException("No idea entity exists in the database");
        }

        Optional<Idea> ideaOptional = Optional.empty();
        long randomId;

        while (ideaOptional.isEmpty()) {
            randomId = Math.abs(random.nextLong(count) + 1);
            ideaOptional = ideasRepository.findById(randomId);
        }

        return new GetIdeaDTO(ideaOptional.get());
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepository.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }
}
