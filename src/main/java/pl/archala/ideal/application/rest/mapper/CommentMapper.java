package pl.archala.ideal.application.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.domain.model.Comment;
import pl.archala.ideal.infrastructure.component.ApplicationTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ApplicationTime applicationTime;

    public Comment toEntity(SaveCommentRequest saveCommentRequest) {
        return Comment.builder()
                      .content(saveCommentRequest.content())
                      .createdAt(applicationTime.now())
                      .build();
    }

    public GetCommentResponse toGetCommentResponse(Comment comment) {
        return new GetCommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt());
    }
}
