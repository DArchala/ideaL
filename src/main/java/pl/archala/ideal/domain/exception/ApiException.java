package pl.archala.ideal.domain.exception;

import lombok.RequiredArgsConstructor;
import pl.archala.ideal.domain.enums.ErrorType;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class ApiException extends RuntimeException {

    private final OffsetDateTime dateTime;
    private final ErrorType errorType;
    private final String message;

}
