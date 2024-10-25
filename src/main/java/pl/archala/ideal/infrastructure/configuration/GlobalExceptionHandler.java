package pl.archala.ideal.infrastructure.configuration;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.archala.ideal.application.rest.dto.out.ApiErrorResponse;
import pl.archala.ideal.application.rest.mapper.ErrorResponseMapper;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorResponseMapper mapper;

    @SuppressWarnings("NullableProblems")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> reasons = ex.getBindingResult()
                                 .getAllErrors()
                                 .stream()
                                 .map(ObjectError::getDefaultMessage)
                                 .toList();
        var apiErrorResponse = mapper.toErrorResponse(reasons, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.status());
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handlePropertyReferenceException(EntityNotFoundException e) {
        List<String> reasons = List.of(e.getMessage());
        ApiErrorResponse apiErrorResponse = mapper.toErrorResponse(reasons, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.status());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> reasons = e.getConstraintViolations()
                                .stream()
                                .map(ConstraintViolation::getMessageTemplate)
                                .toList();
        ApiErrorResponse apiErrorResponse = mapper.toErrorResponse(reasons, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.status());
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    protected ResponseEntity<ApiErrorResponse> handleConstraintViolationException(PropertyReferenceException e) {
        List<String> reasons = List.of(e.getMessage());
        ApiErrorResponse apiErrorResponse = mapper.toErrorResponse(reasons, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.status());
    }
}