package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.utils.IdealLocalDateTime;

@Component
public class IdeaMapper {

    public Idea toEntity(AddIdeaDTO ideaDTO) {
        Idea idea = new Idea();
        idea.setTitle(ideaDTO.title());
        idea.setContent(ideaDTO.content());
        idea.setCategory(ideaDTO.category());
        idea.setCreated(IdealLocalDateTime.now());
        return idea;
    }

    public GetIdeaDTO toDto(Idea idea) {
        return new GetIdeaDTO(idea.getId(), idea.getTitle(), idea.getContent(), idea.getCreated());
    }

}

