package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.*;

public interface CommentsService {

    GetCommentDTO findById(Long id);

    GetCommentDTO save(AddIdeaCommentDTO addIdeaCommentDTO);

    GetCommentDTO save(AddCommentCommentDTO addIdeaCommentDTO);

    GetCommentDTO save(AddRealizationCommentDTO addRealizationCommentDTO);

    GetCommentDTO deleteById(Long id);

    GetCommentDTO updateContent(PatchCommentDTO patchCommentDTO);
}
