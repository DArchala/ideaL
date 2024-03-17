package pl.archala.ideal.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class IdealLocalDateTime {

    public static LocalDateTime now() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
