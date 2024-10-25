package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.comment.UpdateCommentRequest;
import pl.archala.ideal.entity.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ApplicationTime applicationTime;

    public Comment toEntity(SaveCommentRequest saveCommentRequest) {
        Comment comment = new Comment();
        comment.setContent(saveCommentRequest.content());
        comment.setCreatedAt(applicationTime.now());
        return comment;
    }

    public Comment toUpdatedEntity(Comment comment, UpdateCommentRequest updateCommentRequest) {
        if (updateCommentRequest.content() != null) {
            comment.setContent(updateCommentRequest.content());
        }
        return comment;
    }

    public GetCommentResponse toGetDto(Comment comment) {
        return new GetCommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt());
    }
}
