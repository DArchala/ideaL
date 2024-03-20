package pl.archala.ideal.dto.idea;

import pl.archala.ideal.enums.IdeaCategory;

import java.time.LocalDateTime;

public record GetIdeaDTO(Long id, String title, String content, LocalDateTime created, IdeaCategory category) {

}
