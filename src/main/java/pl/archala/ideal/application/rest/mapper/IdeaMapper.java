package pl.archala.ideal.application.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.infrastructure.component.ApplicationTime;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.domain.model.Idea;

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

