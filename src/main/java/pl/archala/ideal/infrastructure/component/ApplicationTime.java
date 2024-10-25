package pl.archala.ideal.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationTime {

    private final Clock clock;

    public OffsetDateTime now() {
        return OffsetDateTime.now(clock);
    }
}
