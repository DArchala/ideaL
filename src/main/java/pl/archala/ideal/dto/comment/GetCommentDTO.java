package pl.archala.ideal.dto.comment;

import java.time.LocalDateTime;

public record GetCommentDTO(Long id, String content, LocalDateTime created) {

}
