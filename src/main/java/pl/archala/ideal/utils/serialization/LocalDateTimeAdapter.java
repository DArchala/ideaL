package pl.archala.ideal.utils.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter dateFormat;
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
    private static final String TIME_ZONE = "Europe/Warsaw";

    public LocalDateTimeAdapter() {
        this.dateFormat = java.time.format.DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                .withZone(ZoneId.of(TIME_ZONE)).localizedBy(Locale.getDefault());
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ZonedDateTime.parse(jsonElement.getAsString(), dateFormat)
                .withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(localDateTime.atZone(ZoneId.systemDefault())));
    }
}
