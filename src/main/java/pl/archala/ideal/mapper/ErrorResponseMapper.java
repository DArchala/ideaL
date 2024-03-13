package pl.archala.ideal.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.archala.ideal.error.ErrorResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ErrorResponseMapper {

    public ErrorResponse toErrorResponse(List<String> reasons, HttpStatus status) {
        return new ErrorResponse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), reasons, status);
    }

}
