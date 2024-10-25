package pl.archala.ideal.domain.service.interfaces;

import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.UpdateCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;

public interface CommentsService {

    GetCommentResponse findById(Long id);

    void deleteById(Long id);

    GetCommentResponse putUpdate(UpdateCommentRequest updateCommentRequest);

    GetCommentResponse addComment(SaveCommentRequest saveCommentRequest);
}
