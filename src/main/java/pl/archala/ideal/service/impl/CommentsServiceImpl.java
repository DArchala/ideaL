package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.PutCommentDTO;
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
    public GetCommentDTO findById(Long id) {
        return commentMapper.toGetDto(findCommentById(id));
    }

    @Override
    public GetCommentDTO addComment(AddCommentDTO addCommentDTO) {
        Comment parentComment = findCommentById(addCommentDTO.parentId());
        Comment savedComment = commentsRepo.save(commentMapper.toEntity(addCommentDTO));
        parentComment.getComments().add(savedComment);
        return commentMapper.toGetDto(savedComment);
    }

    @Override
    public GetCommentDTO deleteById(Long id) {
        Comment comment = findCommentById(id);
        commentsRepo.delete(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetCommentDTO putUpdate(PutCommentDTO putCommentDTO) {
        Comment comment = commentMapper.update(findCommentById(putCommentDTO.id()), putCommentDTO);
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
