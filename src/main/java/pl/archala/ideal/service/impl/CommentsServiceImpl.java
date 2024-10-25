package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.comment.UpdateCommentRequest;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.service.interfaces.CommentsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepo;
    private final CommentMapper commentMapper;

    @Override
    public GetCommentResponse findById(Long id) {
        return commentMapper.toGetDto(findCommentById(id));
    }

    @Override
    public GetCommentResponse addComment(SaveCommentRequest saveCommentRequest) {
        Comment parentComment = findCommentById(saveCommentRequest.parentId());
        Comment childComment = commentsRepo.save(commentMapper.toEntity(saveCommentRequest));
        parentComment.getComments()
                     .add(childComment);
        return commentMapper.toGetDto(childComment);
    }

    @Override
    public GetCommentResponse deleteById(Long id) {
        Comment comment = findCommentById(id);
        commentsRepo.delete(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetCommentResponse putUpdate(UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentMapper.toUpdatedEntity(findCommentById(updateCommentRequest.id()), updateCommentRequest);
        return commentMapper.toGetDto(commentsRepo.save(comment));
    }

    private Comment findCommentById(Long id) {
        Optional<Comment> optionalComment = commentsRepo.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }

}
