package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.error.ApiErrorResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorResponseMapper {

    private final ApplicationTime applicationTime;

    public ApiErrorResponse toErrorResponse(List<String> reasons, HttpStatus status) {
        return new ApiErrorResponse(applicationTime.now(), reasons, status);
    }

}
