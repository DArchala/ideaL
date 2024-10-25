package pl.archala.ideal.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.archala.ideal.component.ApplicationTime;
import pl.archala.ideal.dto.errorResponse.ErrorResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ErrorResponseMapper {

    private final ApplicationTime applicationTime;

    public ErrorResponse toErrorResponse(List<String> reasons, HttpStatus status) {
        return new ErrorResponse(applicationTime.now(), reasons, status);
    }

}
