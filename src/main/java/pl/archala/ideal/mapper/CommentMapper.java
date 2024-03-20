package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.utils.IdealLocalDateTime;

@Component
public class CommentMapper {

    public Comment toEntity(AddCommentDTO addCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(addCommentDTO.content());
        comment.setCreated(IdealLocalDateTime.now());
        return comment;
    }

    public GetCommentDTO toGetDto(Comment comment) {
        return new GetCommentDTO(comment.getId(), comment.getContent(), comment.getCreated());
    }
}
