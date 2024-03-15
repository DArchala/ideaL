package pl.archala.ideal.dto.comment;

import java.util.List;

public record GetCommentDTO(Long id, String content, Long parentCommentId, List<Long> commentsIds) {

}
