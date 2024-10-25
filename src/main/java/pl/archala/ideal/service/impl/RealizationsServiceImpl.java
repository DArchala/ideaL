package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.mapper.RealizationMapper;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.List;
import java.util.Optional;

import static pl.archala.ideal.utils.ExceptionMessage.REALIZATION_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class RealizationsServiceImpl implements RealizationsService {

    private final RealizationsRepository realizationsRepo;
    private final CommentsRepository commentsRepo;
    private final RealizationMapper realizationMapper;
    private final CommentMapper commentMapper;

    @Override
    public GetRealizationDTO findById(Long id) {
        return realizationMapper.toGetDto(findRealizationById(id));
    }

    @Override
    public List<GetRealizationDTO> findAllByIdeaId(Long ideaId) {
        return realizationsRepo.findAllByIdeaId(ideaId)
                               .stream()
                               .map(realizationMapper::toGetDto)
                               .toList();
    }

    @Override
    public GetCommentDTO addComment(AddCommentDTO addCommentDTO) {
        Realization realization = findRealizationById(addCommentDTO.parentId());
        Comment comment = commentsRepo.save(commentMapper.toEntity(addCommentDTO));
        realization.getComments()
                   .add(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetRealizationDTO deleteById(Long id) {
        Realization realization = findRealizationById(id);
        realizationsRepo.delete(realization);
        return realizationMapper.toGetDto(realization);
    }

    private Realization findRealizationById(Long id) {
        Optional<Realization> optionalComment = realizationsRepo.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException(REALIZATION_DOES_NOT_EXIST.formatted(id));
        }
        return optionalComment.get();
    }

}
