package pl.archala.ideal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.repository.wrapper.IdeasRepositoryWrapper;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepositoryWrapper ideasRepo;
    private final IdeaMapper ideaMapper;

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

}
