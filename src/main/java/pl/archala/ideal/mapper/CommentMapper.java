package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.PutCommentDTO;
import pl.archala.ideal.entity.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ApplicationTime applicationTime;

    public Comment toEntity(AddCommentDTO addCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(addCommentDTO.content());
        comment.setCreatedAt(applicationTime.now());
        return comment;
    }

    public Comment toUpdatedEntity(Comment comment, PutCommentDTO putCommentDTO) {
        if (putCommentDTO.content() != null) {
            comment.setContent(putCommentDTO.content());
        }
        return comment;
    }

    public GetCommentDTO toGetDto(Comment comment) {
        return new GetCommentDTO(comment.getId(), comment.getContent(), comment.getCreatedAt());
    }
}
