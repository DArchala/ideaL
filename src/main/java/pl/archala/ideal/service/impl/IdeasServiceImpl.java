package pl.archala.ideal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.mapper.CommentMapper;
import pl.archala.ideal.mapper.IdeaMapper;
import pl.archala.ideal.repository.IdeasRepository;
import pl.archala.ideal.repository.RealizationsRepository;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeasServiceImpl implements IdeasService {

    private final IdeasRepository ideasRepo;
    private final RealizationsRepository realizationsRepo;
    private final IdeaMapper ideaMapper;
    private final CommentMapper commentMapper;

    public GetIdeaDTO findById(Long id) {
        return ideaMapper.toDto(findIdeaById(id));
    }

    public GetIdeaDTO save(AddIdeaDTO ideaDTO) {
        Idea saved = ideasRepo.save(ideaMapper.toEntity(ideaDTO));
        return ideaMapper.toDto(saved);
    }

    public List<GetIdeaDTO> getPage(PageRequest pageRequest) {
        Page<Idea> ideasPage = ideasRepo.findAll(pageRequest);
        return ideasPage.map(ideaMapper::toDto).getContent();
    }

    @Override
    @Transactional
    public GetIdeaDTO deleteById(Long id) {
        Idea idea = findIdeaById(id);

        realizationsRepo.detachIdeaFromRealizations(id);

        ideasRepo.delete(idea);
        return ideaMapper.toDto(idea);
    }

    @Override
    public GetCommentDTO addComment(AddCommentDTO addCommentDTO) {
        Idea idea = findIdeaById(addCommentDTO.parentId());
        Comment comment = commentMapper.toEntity(addCommentDTO);
        idea.getComments().add(comment);

        return commentMapper.toGetDto(comment);
    }

    private Idea findIdeaById(Long id) {
        Optional<Idea> ideaOptional = ideasRepo.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }
}
