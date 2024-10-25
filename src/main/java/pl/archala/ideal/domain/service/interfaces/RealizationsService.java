package pl.archala.ideal.domain.service.interfaces;

import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;

import java.util.List;

public interface RealizationsService {

    GetRealizationResponse findById(Long id);

    List<GetRealizationResponse> findAllByIdeaId(Long ideaId);

    GetCommentResponse save(SaveCommentRequest saveCommentRequest);

    void deleteById(Long id);
}
