package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.*;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.CommentsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final IdeasRepository ideasRepository;
    private final RealizationsRepository realizationsRepository;
    private final CommentMapper commentMapper;

    @Override
    public GetCommentDTO findById(Long id) {
        return commentMapper.toGetDto(findCommentById(id));
    }

    @Override
    @Transactional
    public GetCommentDTO save(AddIdeaCommentDTO addIdeaCommentDTO) {
        Comment comment = commentMapper.toEntity(addIdeaCommentDTO);
        Idea idea = findIdeaById(addIdeaCommentDTO.ideaId());

        idea.getComments().add(comment);

        return commentMapper.toGetDto(commentsRepository.save(comment));
    }

    @Override
    @Transactional
    public GetCommentDTO save(AddCommentCommentDTO addCommentCommentDTO) {
        Comment newComment = commentMapper.toEntity(addCommentCommentDTO);
        Comment parentComment = findCommentById(addCommentCommentDTO.parentCommentId());

        newComment.setParentComment(parentComment);
        parentComment.getComments().add(newComment);

        return commentMapper.toGetDto(commentsRepository.save(newComment));
    }

    @Override
    public GetCommentDTO save(AddRealizationCommentDTO addRealizationCommentDTO) {
        Comment newComment = commentMapper.toEntity(addRealizationCommentDTO);
        Realization realization = findRealizationById(addRealizationCommentDTO.realizationId());

        realization.getComments().add(newComment);

        return commentMapper.toGetDto(commentsRepository.save(newComment));
    }

    @Override
    public GetCommentDTO deleteById(Long id) {
        Comment comment = findCommentById(id);
        commentsRepository.delete(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetCommentDTO updateContent(PatchCommentDTO patchCommentDTO) {
        Comment comment = findCommentById(patchCommentDTO.id());
        comment.setContent(patchCommentDTO.content());
        return commentMapper.toGetDto(commentsRepository.save(comment));
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepository.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }

    private Comment findCommentById(Long id) {
        Optional<Comment> optionalComment = commentsRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }

    private Realization findRealizationById(Long id) {
        Optional<Realization> optionalComment = realizationsRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }
}
