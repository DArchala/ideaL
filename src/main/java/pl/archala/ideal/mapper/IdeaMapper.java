package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        List<Long> commentsIds = null;
        List<Long> realizationsIds = null;

        if (idea.getOptionalComments().isPresent()) {
            commentsIds = idea.getOptionalComments().get().stream().map(Comment::getId).toList();
        }

        if (idea.getOptionalRealizations().isPresent()) {
            realizationsIds = idea.getOptionalRealizations().get().stream().map(Realization::getId).toList();
        }

        return new GetIdeaDTO(idea.getId(), idea.getTitle(), idea.getContent(), idea.getCreated(), commentsIds, realizationsIds);

    }

}

