package pl.archala.ideal.dto.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.realization.GetSimpleRealizationDTO;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetIdeaDTO {

    private final Long id;
    private final String name;
    private final String content;
    private final LocalDateTime created;
    private final List<GetCommentDTO> comments;
    private final List<GetSimpleRealizationDTO> realizations;

    public GetIdeaDTO(Idea idea) {
        this.id = idea.getId();
        this.name = idea.getName();
        this.content = idea.getContent();
        this.created = idea.getCreated();
        this.comments = idea.getComments().stream().map(GetCommentDTO::new).toList();
        this.realizations = idea.getRealizations().stream().map(GetSimpleRealizationDTO::new).toList();
    }
}
