package pl.archala.ideal.utils.serialization;

public class LocalDateTimeAdapterProvider {

    private static volatile LocalDateTimeAdapter localDateTimeAdapter;

    public static LocalDateTimeAdapter getInstance() {
        LocalDateTimeAdapter result = localDateTimeAdapter;
        if (result != null) {
            return result;
        }
        synchronized (LocalDateTimeAdapter.class) {
            if (localDateTimeAdapter == null) {
                localDateTimeAdapter = new LocalDateTimeAdapter();
            }
            return localDateTimeAdapter;
        }
    }
}
