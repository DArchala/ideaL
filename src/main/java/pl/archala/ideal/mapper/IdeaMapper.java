package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Component
public class IdeaMapper {

    public Idea toEntity(AddIdeaDTO ideaDTO) {
        Idea idea = new Idea();
        idea.setTitle(ideaDTO.title());
        idea.setContent(ideaDTO.content());
        idea.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return idea;
    }
}
