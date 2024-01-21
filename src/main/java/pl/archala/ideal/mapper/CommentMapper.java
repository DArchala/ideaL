package pl.archala.ideal.mapper;

import org.springframework.stereotype.Component;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.entity.Comment;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public Comment toEntity(AddCommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());
        comment.setCreated(LocalDateTime.now());
        return comment;
    }
}
