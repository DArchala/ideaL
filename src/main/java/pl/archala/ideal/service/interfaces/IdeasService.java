package pl.archala.ideal.service.interfaces;

import org.springframework.data.domain.PageRequest;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.dto.idea.GetSimpleIdeaDTO;

import java.util.List;

public interface IdeasService {

    GetIdeaDTO getById(Long id);

    GetSimpleIdeaDTO save(AddIdeaDTO ideaDTO);

    List<GetSimpleIdeaDTO> getPage(PageRequest pageRequest);
}
