package pl.archala.ideal.service.interfaces;

import org.springframework.data.domain.PageRequest;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;

import java.util.List;

public interface IdeasService {

    GetIdeaDTO findById(Long id);

    GetIdeaDTO save(AddIdeaDTO ideaDTO);

    List<GetIdeaDTO> getPage(PageRequest pageRequest);

    GetIdeaDTO deleteById(Long id);

    GetCommentDTO addComment(AddCommentDTO addCommentDTO);

}
