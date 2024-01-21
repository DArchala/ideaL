package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.GetSimpleCommentDTO;

public interface CommentsService {

    GetCommentDTO findById(Long id);

    GetCommentDTO save(AddCommentDTO addCommentDTO);

    GetSimpleCommentDTO deleteById(Long id);
}
