package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.comment.UpdateCommentRequest;

public interface CommentsService {

    GetCommentResponse findById(Long id);

    GetCommentResponse deleteById(Long id);

    GetCommentResponse putUpdate(UpdateCommentRequest updateCommentRequest);

    GetCommentResponse addComment(SaveCommentRequest saveCommentRequest);
}
