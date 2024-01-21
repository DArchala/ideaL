package pl.archala.ideal.dto.idea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.ideal.dto.comment.GetSimpleCommentDTO;
import pl.archala.ideal.entity.Idea;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetIdeaDTO {

    private final Long id;
    private final String name;
    private final String content;
    private final List<GetSimpleCommentDTO> comments;
    private final LocalDateTime created;

    public GetIdeaDTO(Idea idea) {
        this.id = idea.getId();
        this.name = idea.getName();
        this.content = idea.getContent();
        this.comments = idea.getComments().stream().map(GetSimpleCommentDTO::new).toList();
        this.created = idea.getCreated();
    }
}
