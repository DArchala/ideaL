package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.AddRealizationCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.utils.IdealLocalDateTime;

@Component
public class CommentMapper {

    public Comment toEntity(AddIdeaCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(IdealLocalDateTime.now());
        return comment;
    }

    public Comment toEntity(AddCommentCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(IdealLocalDateTime.now());
        return comment;
    }

    public Comment toEntity(AddRealizationCommentDTO addRealizationCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(addRealizationCommentDTO.content());
        comment.setCreated(IdealLocalDateTime.now());
        return comment;
    }

    public GetCommentDTO toGetDto(Comment comment) {
        return new GetCommentDTO(comment.getId(), comment.getContent(), comment.getCreated());
    }
}
