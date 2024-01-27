package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.PatchCommentDTO;

public interface CommentsService {

    GetCommentDTO findById(Long id);

    GetCommentDTO save(AddIdeaCommentDTO addIdeaCommentDTO);

    GetCommentDTO save(AddCommentCommentDTO addIdeaCommentDTO);

    GetCommentDTO deleteById(Long id);

    GetCommentDTO updateContent(PatchCommentDTO patchCommentDTO);
}
