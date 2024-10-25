package pl.archala.ideal.domain.service.interfaces;

import org.springframework.data.domain.PageRequest;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.application.rest.dto.in.SaveRealizationRequest;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;

import java.util.List;

public interface IdeasService {

    GetIdeaResponse findById(Long id);

    GetIdeaResponse save(SaveIdeaRequest ideaDTO);

    List<GetIdeaResponse> getPage(PageRequest pageRequest);

    GetIdeaResponse deleteById(Long id);

    GetCommentResponse addComment(SaveCommentRequest saveCommentRequest);

    GetRealizationResponse addRealization(SaveRealizationRequest saveRealizationRequest);
}
