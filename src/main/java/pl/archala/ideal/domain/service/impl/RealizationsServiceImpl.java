package pl.archala.ideal.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.domain.model.Comment;
import pl.archala.ideal.domain.model.Realization;
import pl.archala.ideal.application.rest.mapper.CommentMapper;
import pl.archala.ideal.application.rest.mapper.RealizationMapper;
import pl.archala.ideal.application.database.repository.CommentsRepository;
import pl.archala.ideal.application.database.repository.RealizationsRepository;
import pl.archala.ideal.domain.service.interfaces.RealizationsService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RealizationsServiceImpl implements RealizationsService {

    private final RealizationsRepository realizationsRepo;
    private final CommentsRepository commentsRepo;
    private final RealizationMapper realizationMapper;
    private final CommentMapper commentMapper;

    @Override
    public GetRealizationResponse findById(Long id) {
        return realizationMapper.toGetDto(findRealizationById(id));
    }

    @Override
    public List<GetRealizationResponse> findAllByIdeaId(Long ideaId) {
        return realizationsRepo.findAllByIdeaId(ideaId)
                               .stream()
                               .map(realizationMapper::toGetDto)
                               .toList();
    }

    @Override
    public GetCommentResponse addComment(SaveCommentRequest saveCommentRequest) {
        Realization realization = findRealizationById(saveCommentRequest.parentId());
        Comment comment = commentsRepo.save(commentMapper.toEntity(saveCommentRequest));
        realization.getComments()
                   .add(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetRealizationResponse deleteById(Long id) {
        Realization realization = findRealizationById(id);
        realizationsRepo.delete(realization);
        return realizationMapper.toGetDto(realization);
    }

    private Realization findRealizationById(Long id) {
        Optional<Realization> optionalComment = realizationsRepo.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }

}
