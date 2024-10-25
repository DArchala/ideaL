package pl.archala.ideal.dto.comment;

import java.time.OffsetDateTime;

public record GetCommentResponse(Long id, String content, OffsetDateTime createdAt) {

}
