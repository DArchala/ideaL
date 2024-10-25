package pl.archala.ideal.service.interfaces;

import org.springframework.data.domain.PageRequest;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.idea.SaveIdeaRequest;
import pl.archala.ideal.dto.idea.GetIdeaResponse;
import pl.archala.ideal.dto.realization.SaveRealizationRequest;
import pl.archala.ideal.dto.realization.GetRealizationResponse;

import java.util.List;

public interface IdeasService {

    GetIdeaResponse findById(Long id);

    GetIdeaResponse save(SaveIdeaRequest ideaDTO);

    List<GetIdeaResponse> getPage(PageRequest pageRequest);

    GetIdeaResponse deleteById(Long id);

    GetCommentResponse addComment(SaveCommentRequest saveCommentRequest);

    GetRealizationResponse addRealization(SaveRealizationRequest saveRealizationRequest);
}
