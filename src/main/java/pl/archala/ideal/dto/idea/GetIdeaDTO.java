package pl.archala.ideal.dto.idea;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

public record GetIdeaDTO(Long id, String title, String content, @EqualsAndHashCode.Include() LocalDateTime created,
                         @JsonInclude(JsonInclude.Include.NON_EMPTY) List<Long> commentsIds,
                         @JsonInclude(JsonInclude.Include.NON_EMPTY) List<Long> realizationsIds) {

}
