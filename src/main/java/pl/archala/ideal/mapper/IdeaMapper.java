package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.idea.SaveIdeaRequest;
import pl.archala.ideal.dto.idea.GetIdeaResponse;
import pl.archala.ideal.entity.Idea;

@Component
@RequiredArgsConstructor
public class IdeaMapper {

    private final ApplicationTime applicationTime;

    public Idea toEntity(SaveIdeaRequest ideaDTO) {
        return Idea.builder()
                       .title(ideaDTO.title())
                       .content(ideaDTO.content())
                       .category(ideaDTO.category())
                       .createdAt(applicationTime.now())
                       .build();
    }

    public GetIdeaResponse toGetDto(Idea idea) {
        return new GetIdeaResponse(idea.getId(), idea.getTitle(), idea.getContent(), idea.getCreatedAt(), idea.getCategory());
    }

}

