package pl.archala.ideal.dto.error;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiErrorResponse(OffsetDateTime occurred,
                               List<String> reasons,
                               HttpStatus status) {
}
