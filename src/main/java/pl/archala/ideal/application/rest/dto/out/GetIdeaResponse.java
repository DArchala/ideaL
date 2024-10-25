package pl.archala.ideal.application.rest.dto.out;

import pl.archala.ideal.domain.enums.IdeaCategory;

import java.time.OffsetDateTime;

public record GetIdeaResponse(Long id, String title, String content, OffsetDateTime createdAt, IdeaCategory category) {

}
