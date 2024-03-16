package pl.archala.ideal.dto.idea;

import java.time.LocalDateTime;

public record GetIdeaDTO(Long id, String title, String content, LocalDateTime created) {

}
