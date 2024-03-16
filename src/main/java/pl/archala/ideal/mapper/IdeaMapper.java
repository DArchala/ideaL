package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class IdeaMapper {

    public Idea toEntity(AddIdeaDTO ideaDTO) {
        Idea idea = new Idea();
        idea.setTitle(ideaDTO.title());
        idea.setContent(ideaDTO.content());
        idea.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return idea;
    }

    public GetIdeaDTO toDto(Idea idea) {
        return new GetIdeaDTO(idea.getId(), idea.getTitle(), idea.getContent(), idea.getCreated());
    }

}

