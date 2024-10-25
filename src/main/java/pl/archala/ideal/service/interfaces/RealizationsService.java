package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.realization.GetRealizationResponse;

import java.util.List;

public interface RealizationsService {

    GetRealizationResponse findById(Long id);

    List<GetRealizationResponse> findAllByIdeaId(Long ideaId);

    GetCommentResponse addComment(SaveCommentRequest saveCommentRequest);

    GetRealizationResponse deleteById(Long id);
}
