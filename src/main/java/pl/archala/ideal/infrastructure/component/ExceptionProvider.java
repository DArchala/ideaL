package pl.archala.ideal.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.archala.ideal.domain.enums.ErrorType;
import pl.archala.ideal.domain.exception.ApiException;

@Component
@RequiredArgsConstructor
public class ExceptionProvider {

    private final ApplicationTime applicationTime;

    public ApiException apiException(ErrorType errorType, String message) {
        return new ApiException(applicationTime.now(), errorType, message);
    }

}
