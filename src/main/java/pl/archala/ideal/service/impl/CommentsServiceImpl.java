package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.service.interfaces.CommentsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final IdeasRepository ideasRepository;
    private final CommentMapper commentMapper;

    @Override
    public GetCommentDTO findById(Long id) {
        Optional<Comment> optionalComment = commentsRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment with id %d does not exist".formatted(id));
        }
        return new GetCommentDTO(optionalComment.get());
    }

    @Override
    public GetCommentDTO save(AddCommentDTO addCommentDTO) {
        Comment comment = commentMapper.toEntity(addCommentDTO);
        Optional<Idea> ideaOptional = ideasRepository.findById(addCommentDTO.ideaId());
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(addCommentDTO.ideaId()));
        }
        comment.setIdea(ideaOptional.get());
        Comment savedComment = commentsRepository.save(comment);
        return new GetCommentDTO(savedComment);
    }

}
