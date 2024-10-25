package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.PutCommentDTO;

public interface CommentsService {

    GetCommentDTO findById(Long id);

    GetCommentDTO deleteById(Long id);

    GetCommentDTO putUpdate(PutCommentDTO putCommentDTO);

    GetCommentDTO addComment(AddCommentDTO addCommentDTO);
}
