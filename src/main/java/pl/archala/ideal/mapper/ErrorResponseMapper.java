package pl.archala.ideal.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.archala.ideal.error.ErrorResponse;
import pl.archala.ideal.utils.IdealLocalDateTime;

import java.util.List;

@Component
public class ErrorResponseMapper {

    public ErrorResponse toErrorResponse(List<String> reasons, HttpStatus status) {
        return new ErrorResponse(IdealLocalDateTime.now(), reasons, status);
    }

}
