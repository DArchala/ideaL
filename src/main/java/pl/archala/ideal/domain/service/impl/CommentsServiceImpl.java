package pl.archala.ideal.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.archala.ideal.application.database.repository.CommentsRepository;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.in.UpdateCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.mapper.CommentMapper;
import pl.archala.ideal.domain.enums.ErrorType;
import pl.archala.ideal.domain.model.Comment;
import pl.archala.ideal.domain.service.interfaces.CommentsService;
import pl.archala.ideal.infrastructure.component.ExceptionProvider;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final ExceptionProvider exceptionProvider;
    private final CommentsRepository commentsRepo;
    private final CommentMapper commentMapper;

    @Override
    public GetCommentResponse findById(Long id) {
        return commentMapper.toGetDto(findCommentById(id));
    }

    @Override
    public GetCommentResponse addComment(SaveCommentRequest saveCommentRequest) {
        var parentComment = findCommentById(saveCommentRequest.parentId());
        var childComment = commentsRepo.save(commentMapper.toEntity(saveCommentRequest));
        parentComment.addComment(childComment);
        return commentMapper.toGetDto(childComment);
    }

    @Override
    public void deleteById(Long id) {
        commentsRepo.deleteById(id);
    }

    @Override
    public GetCommentResponse putUpdate(UpdateCommentRequest updateCommentRequest) {
        var comment = commentMapper.toUpdatedEntity(findCommentById(updateCommentRequest.id()), updateCommentRequest);
        return commentMapper.toGetDto(commentsRepo.save(comment));
    }

    private Comment findCommentById(Long id) {
        return commentsRepo.findById(id)
                           .orElseThrow(() -> exceptionProvider.apiException(ErrorType.COMMENT_NOT_FOUND, "Comment with id %d does not exist".formatted(id)));
    }

}
