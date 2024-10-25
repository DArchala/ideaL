package pl.archala.ideal.dto.idea;

import pl.archala.ideal.enums.IdeaCategory;

import java.time.OffsetDateTime;

public record GetIdeaResponse(Long id, String title, String content, OffsetDateTime createdAt, IdeaCategory category) {

}
