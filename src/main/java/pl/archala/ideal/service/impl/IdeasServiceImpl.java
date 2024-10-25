package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.mapper.RealizationMapper;
import pl.archala.ideal.repository.CommentsRepository;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepository ideasRepo;
    private final RealizationsRepository realizationsRepo;
    private final CommentsRepository commentsRepo;
    private final IdeaMapper ideaMapper;
    private final RealizationMapper realizationMapper;
    private final CommentMapper commentMapper;

    public GetIdeaDTO findById(Long id) {
        return ideaMapper.toGetDto(findIdeaById(id));
    }

    public GetIdeaDTO save(AddIdeaDTO ideaDTO) {
        return ideaMapper.toGetDto(ideasRepo.save(ideaMapper.toEntity(ideaDTO)));
    }

    public List<GetIdeaDTO> getPage(PageRequest pageRequest) {
        return ideasRepo.findAll(pageRequest)
                        .map(ideaMapper::toGetDto)
                        .getContent();
    }

    @Override
    public GetIdeaDTO deleteById(Long id) {
        Idea idea = findIdeaById(id);
        realizationsRepo.detachIdeaFromRealizations(id);
        ideasRepo.delete(idea);
        return ideaMapper.toGetDto(idea);
    }

    @Override
    public GetCommentDTO addComment(AddCommentDTO addCommentDTO) {
        Idea idea = findIdeaById(addCommentDTO.parentId());
        Comment comment = commentsRepo.save(commentMapper.toEntity(addCommentDTO));
        idea.getComments()
            .add(comment);
        return commentMapper.toGetDto(comment);
    }

    @Override
    public GetRealizationDTO addRealization(AddRealizationDTO addRealizationDTO) {
        Idea idea = findIdeaById(addRealizationDTO.ideaId());
        Realization realization = realizationsRepo.save(realizationMapper.toEntity(addRealizationDTO));

        realization.setIdea(idea);
        idea.getRealizations()
            .add(realization);

        return realizationMapper.toGetDto(realization);
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepo.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }
}
