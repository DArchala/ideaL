package pl.archala.ideal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.*;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.repository.wrapper.CommentsRepositoryWrapper;
import pl.archala.ideal.repository.wrapper.IdeasRepositoryWrapper;
import pl.archala.ideal.repository.wrapper.RealizationsRepositoryWrapper;
import pl.archala.ideal.service.interfaces.CommentsService;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepositoryWrapper commentsRepo;
    private final IdeasRepositoryWrapper ideasRepo;
    private final RealizationsRepositoryWrapper realizationsRepo;
    private final CommentMapper commentMapper;

    @Override
    public GetCommentDTO findById(Long id) {
        return commentMapper.toGetDto(commentsRepo.findById(id));
    }

    @Override
    @Transactional
    public GetCommentDTO save(AddIdeaCommentDTO addIdeaCommentDTO) {
        Comment comment = commentMapper.toEntity(addIdeaCommentDTO);
        Idea idea = ideasRepo.findById(addIdeaCommentDTO.ideaId());

        idea.getComments().add(comment);

        return commentMapper.toGetDto(commentsRepo.save(comment));
    }

    @Override
    @Transactional
    public GetCommentDTO save(AddCommentCommentDTO addCommentCommentDTO) {
        Comment newComment = commentMapper.toEntity(addCommentCommentDTO);
        Comment parentComment = commentsRepo.findById(addCommentCommentDTO.parentCommentId());

        parentComment.getComments().add(newComment);

        return commentMapper.toGetDto(commentsRepo.save(newComment));
    }

    @Override
    @Transactional
    public GetCommentDTO save(AddRealizationCommentDTO addRealizationCommentDTO) {
        Comment newComment = commentMapper.toEntity(addRealizationCommentDTO);
        Realization realization = realizationsRepo.findById(addRealizationCommentDTO.realizationId());

        realization.getComments().add(newComment);

        return commentMapper.toGetDto(commentsRepo.save(newComment));
    }

    @Override
    public GetCommentDTO deleteById(Long id) {
        Comment comment = commentsRepo.findById(id);
        commentsRepo.delete(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetCommentDTO updateContent(PatchCommentDTO patchCommentDTO) {
        Comment comment = commentsRepo.findById(patchCommentDTO.id());
        comment.setContent(patchCommentDTO.content());
        return commentMapper.toGetDto(commentsRepo.save(comment));
    }

}
