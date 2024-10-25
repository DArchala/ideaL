package pl.archala.ideal.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.application.database.repository.CommentsRepository;
import pl.archala.ideal.application.database.repository.RealizationsRepository;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.application.rest.mapper.CommentMapper;
import pl.archala.ideal.application.rest.mapper.RealizationMapper;
import pl.archala.ideal.domain.enums.ErrorType;
import pl.archala.ideal.domain.model.Realization;
import pl.archala.ideal.domain.service.interfaces.RealizationsService;
import pl.archala.ideal.infrastructure.component.ExceptionProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RealizationsServiceImpl implements RealizationsService {

    private final ExceptionProvider exceptionProvider;
    private final RealizationsRepository realizationsRepo;
    private final CommentsRepository commentsRepo;
    private final RealizationMapper realizationMapper;
    private final CommentMapper commentMapper;

    @Override
    public GetRealizationResponse findById(Long id) {
        return realizationMapper.toGetRealizationResponse(findRealizationById(id));
    }

    @Override
    public List<GetRealizationResponse> findAllByIdeaId(Long ideaId) {
        return realizationsRepo.findAllByIdeaId(ideaId)
                               .stream()
                               .map(realizationMapper::toGetRealizationResponse)
                               .toList();
    }

    @Override
    public GetCommentResponse save(SaveCommentRequest saveCommentRequest) {
        var realization = findRealizationById(saveCommentRequest.parentId());
        var comment = commentsRepo.save(commentMapper.toEntity(saveCommentRequest));
        realization.addComment(comment);
        return commentMapper.toGetCommentResponse(comment);
    }

    @Override
    public void deleteById(Long id) {
        realizationsRepo.deleteById(id);
    }

    private Realization findRealizationById(Long id) {
        return realizationsRepo.findById(id)
                               .orElseThrow(() -> exceptionProvider.apiException(ErrorType.REALIZATION_NOT_FOUND,
                                                                                 "Realization with id %d does not exist".formatted(id)));
    }

}
