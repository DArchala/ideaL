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
import pl.archala.ideal.repository.wrapper.IdeasRepositoryWrapper;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepositoryWrapper ideasRepo;
    private final IdeaMapper ideaMapper;
    private final Random random = new Random();

    public GetIdeaDTO findById(Long id) {
        return ideaMapper.toDto(ideasRepo.findById(id));
    }

    public GetIdeaDTO save(AddIdeaDTO ideaDTO) {
        Idea saved = ideasRepo.save(ideaMapper.toEntity(ideaDTO));
        return ideaMapper.toDto(saved);
    }

    public List<GetIdeaDTO> getPage(PageRequest pageRequest) {
        Page<Idea> ideasPage = ideasRepo.findAll(pageRequest);
        return ideasPage.map(ideaMapper::toDto).getContent();
    }

    @Override
    public GetIdeaDTO deleteById(Long id) {
        Idea idea = ideasRepo.findById(id);
        ideasRepo.delete(idea);
        return ideaMapper.toDto(idea);
    }

    @Override
    public GetIdeaDTO getRandom(IdeaCategory category) {
        long count = ideasRepo.count();
        if (count == 0) {
            throw new EntityNotFoundException("No idea entity exists in the database");
        }

        Optional<Idea> ideaOptional = Optional.empty();
        long randomId;

        while (ideaOptional.isEmpty()) {
            randomId = Math.abs(random.nextLong(count) + 1);
            ideaOptional = Optional.of(ideasRepo.findById(randomId));
        }

        return ideaMapper.toDto(ideaOptional.get());
    }

}
