package pl.archala.ideal.application.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.UpdateCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.domain.model.Comment;
import pl.archala.ideal.infrastructure.component.ApplicationTime;

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
