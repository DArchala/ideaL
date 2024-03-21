package pl.archala.ideal.service.interfaces;

import pl.archala.ideal.dto.comment.*;

public interface CommentsService {

    GetCommentDTO findById(Long id);

    GetCommentDTO deleteById(Long id);

    GetCommentDTO putUpdate(PutCommentDTO putCommentDTO);

    GetCommentDTO addComment(AddCommentDTO addCommentDTO);
}
