package pl.archala.ideal.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;

import java.util.List;

@Service
public interface IdeasService {

    GetIdeaDTO getById(Long id);

    GetIdeaDTO save(AddIdeaDTO ideaDTO);

    List<GetIdeaDTO> getPage(PageRequest pageRequest);
}
