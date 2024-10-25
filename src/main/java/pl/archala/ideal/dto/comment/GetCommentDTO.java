package pl.archala.ideal.dto.comment;

import java.time.OffsetDateTime;

public record GetCommentDTO(Long id, String content, OffsetDateTime created) {

}
