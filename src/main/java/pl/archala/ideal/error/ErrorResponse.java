package pl.archala.ideal.error;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime occurred;
    private List<String> reasons;
    private HttpStatus status;

    public ErrorResponse(List<String> reasons, HttpStatus status) {
        this.occurred = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.reasons = reasons;
        this.status = status;
    }
}
