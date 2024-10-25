package pl.archala.ideal.application.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.archala.ideal.application.rest.dto.out.ApiErrorResponse;
import pl.archala.ideal.infrastructure.component.ApplicationTime;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorResponseMapper {

    private final ApplicationTime applicationTime;

    public ApiErrorResponse toErrorResponse(List<String> reasons, HttpStatus status) {
        return new ApiErrorResponse(applicationTime.now(), reasons, status);
    }

}
