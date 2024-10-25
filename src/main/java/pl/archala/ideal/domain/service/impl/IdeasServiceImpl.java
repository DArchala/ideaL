package pl.archala.ideal.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.application.database.repository.CommentsRepository;
import pl.archala.ideal.application.database.repository.IdeasRepository;
import pl.archala.ideal.application.database.repository.RealizationsRepository;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.in.SaveRealizationRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.application.rest.mapper.CommentMapper;
import pl.archala.ideal.application.rest.mapper.IdeaMapper;
import pl.archala.ideal.application.rest.mapper.RealizationMapper;
import pl.archala.ideal.domain.enums.ErrorType;
import pl.archala.ideal.domain.model.Idea;
import pl.archala.ideal.domain.service.interfaces.IdeasService;
import pl.archala.ideal.infrastructure.component.ExceptionProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeasServiceImpl implements IdeasService {

    private final ExceptionProvider exceptionProvider;
    private final IdeasRepository ideasRepository;
    private final RealizationsRepository realizationsRepository;
    private final CommentsRepository commentsRepository;
    private final IdeaMapper ideaMapper;
    private final RealizationMapper realizationMapper;
    private final CommentMapper commentMapper;

    public GetIdeaResponse findById(Long id) {
        return ideaMapper.toGetDto(findIdeaById(id));
    }

    public GetIdeaResponse save(SaveIdeaRequest ideaDTO) {
        return ideaMapper.toGetDto(ideasRepository.save(ideaMapper.toEntity(ideaDTO)));
    }

    public List<GetIdeaResponse> getPage(PageRequest pageRequest) {
        return ideasRepository.findAll(pageRequest)
                              .map(ideaMapper::toGetDto)
                              .getContent();
    }

    @Override
    public GetIdeaResponse deleteById(Long id) {
        var idea = findIdeaById(id);
        realizationsRepository.detachIdeaFromRealizations(id);
        ideasRepository.delete(idea);
        return ideaMapper.toGetDto(idea);
    }

    @Override
    public GetCommentResponse addComment(SaveCommentRequest saveCommentRequest) {
        var idea = findIdeaById(saveCommentRequest.parentId());
        var comment = commentsRepository.save(commentMapper.toEntity(saveCommentRequest));
        idea.addComment(comment);
        return commentMapper.toGetCommentResponse(comment);
    }

    @Override
    @Transactional
    public GetRealizationResponse addRealization(SaveRealizationRequest saveRealizationRequest) {
        var idea = findIdeaById(saveRealizationRequest.ideaId());
        var realization = realizationsRepository.save(realizationMapper.toEntity(saveRealizationRequest));

        realization.assignIdea(idea);
        idea.addRealization(realization);

        return realizationMapper.toGetRealizationResponse(realization);
    }

    private Idea findIdeaById(Long id) {
        return ideasRepository.findById(id)
                              .orElseThrow(() -> exceptionProvider.apiException(ErrorType.IDEA_NOT_FOUND, "Realization with id %d does not exist".formatted(id)));
    }
}
