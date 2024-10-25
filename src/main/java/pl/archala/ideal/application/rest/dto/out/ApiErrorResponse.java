package pl.archala.ideal.application.rest.dto.out;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiErrorResponse(OffsetDateTime occurred,
                               List<String> reasons,
                               HttpStatus status) {
}
