package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;

@Component
public class IdeaMapper {

    public GetIdeaDTO toGetDTO(Idea idea) {
        return new GetIdeaDTO(idea);
    }

    public Idea toEntity(AddIdeaDTO ideaDTO) {
        Idea idea = new Idea();
        idea.setName(ideaDTO.getName());
        idea.setContent(ideaDTO.getContent());
        idea.setCreated(ideaDTO.getCreate());
        return idea;
    }
}
