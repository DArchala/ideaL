package pl.archala.ideal.dto.errorResponse;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(OffsetDateTime occurred,
                            List<String> reasons,
                            HttpStatus status) {
}
