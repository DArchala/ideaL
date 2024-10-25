package pl.archala.ideal.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    IDEA_NOT_FOUND(HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    REALIZATION_NOT_FOUND(HttpStatus.NOT_FOUND),
    ;

    private final HttpStatus httpStatus;
}
