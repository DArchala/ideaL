package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Component
public class CommentMapper {

    public Comment toEntity(AddIdeaCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(LocalDateTime.now());
        return comment;
    }

    public Comment toEntity(AddCommentCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(LocalDateTime.now());
        return comment;
    }
}
