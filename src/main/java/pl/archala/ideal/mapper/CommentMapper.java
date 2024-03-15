package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.AddRealizationCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class CommentMapper {

    public Comment toEntity(AddIdeaCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return comment;
    }

    public Comment toEntity(AddCommentCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return comment;
    }

    public Comment toEntity(AddRealizationCommentDTO addRealizationCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(addRealizationCommentDTO.content());
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return comment;
    }

    public GetCommentDTO toGetDto(Comment comment) {
        List<Long> commentsIds = null;

        if (comment.getOptionalComments().isPresent()) {
            commentsIds = comment.getOptionalComments().get().stream().map(Comment::getId).toList();
        }

        return new GetCommentDTO(comment.getId(), comment.getContent(), comment.getParentComment().getId(), commentsIds);
    }
}
